package org.jetbrains.kotlin.infra.jps

import org.jetbrains.kotlin.infra.jps.Lib.Companion.CACHE_PATH_IN_HOME
import java.io.File
import java.lang.System.exit

fun main(args: Array<String>) {
    if (args.size != 1) {
        printUsage()
        exit(1)
    }

    val projectDir = File(args[0])
//    val projectDir = File("/Users/jetbrains/tasks/kwjps/wjps")
    val artifactsDir = projectDir.resolve(".idea/artifacts")

    println("Updating gradle cache references in project $projectDir")
    println("Artifacts dir: $artifactsDir")

    if (!artifactsDir.isDirectory) {
        println("$artifactsDir is not a directory.")
        printUsage()

        exit(1)
    }

    // sample reference:
    // "$USER_HOME$/.gradle/caches/modules-2/files-2.1/
    // org.jetbrains.kotlin/kotlin-stdlib-minimal-for-test/
    // 1.3.30-dev-1419/
    // 3fc425e5a7e1979aad08d0f420f07c19c9c36d63/
    // kotlin-stdlib-minimal-for-test-1.3.30-dev-1419.jar"

    val refFile = artifactsDir.resolve("dist_auto_stdlib_reference_dont_use.xml")
    if (!refFile.isFile) {
        println("Please import project with jpsBuild enabled (https://jetbrains.quip.com/ynohAbVMwQWT/Building-Kotlin-via-JPS)")
        println("Cannot find $refFile")
        exit(0)
    }

    val ref = refFile.readText()
    val libs = mutableListOf<Lib>()
    ref.lines().forEach {
        val a = it.trim()
        val prefix = "<element id=\"file-copy\" path=\""
        if (a.startsWith(prefix)) {
            val path = a.removePrefix(prefix).removeSuffix("\" />")
            libs.add(Lib(path))
        }
    }

    with(Replacer()) {

        artifactsDir.listFiles()?.forEach { xmlFile ->
            if (xmlFile.isFile && !xmlFile.name.startsWith("dist_auto_")) {
                var txt = xmlFile.readText()
                libs.forEach { lib ->
                    txt = replace(lib, txt, xmlFile.relativeTo(artifactsDir).path)
                }
                xmlFile.writeText(txt)
            }
        }

        if (replaced > 0) {
            println("Done. $replaced references updated.")
        } else {
            println("Everything already up to date.")
        }
    }
}

open class Replacer {
    var replaced = 0

    fun replace(
        lib: Lib,
        txt: String,
        relativePath: String
    ): String {
        return lib.regex.replace(txt) {
            val srcValue = it.value.removeSuffix("\"")
            if (srcValue != CACHE_PATH_IN_HOME + lib.pathA) {
                val old = Lib(srcValue)
                if (old.configuration == lib.configuration) {
                    report("$relativePath replacing: ${lib.group}:${lib.artifact}:${old.version}:${lib.configuration} -> ${lib.version} [${old.hash} -> ${lib.hash}]")
                    replaced++

                    "${CACHE_PATH_IN_HOME}${lib.pathA}\""
                } else it.value
            } else it.value
        }
    }

    open fun report(msg: String) {
        println(msg)
    }

}

private fun printUsage() {
    println("Usage: jps-update-bootstrap /path/to/kotlin-project")
}

class Lib(path: String) {
    val pathA = path.substringAfter(CACHE_PATH_IN_HOME).removeSuffix("\"")

    // 0: org.jetbrains.kotlin/
    // 1: kotlin-stdlib-minimal-for-test/
    // 2: 1.3.30-dev-1419/
    // 3: 3fc425e5a7e1979aad08d0f420f07c19c9c36d63/
    // 4: kotlin-stdlib-minimal-for-test-1.3.30-dev-1419.jar

    val parts = pathA.split("/")
    val group = parts[0]
    val artifact = parts[1]
    val version = parts[2]
    val hash = parts[3]
    val filename = parts[4]
    val configuration = filename.removePrefix("$artifact-$version").removeSuffix(".jar")
    val regex = Regex(
        "${Regex.escape(CACHE_PATH_IN_HOME)}$group/$artifact/([^\"]+)\"".trim()
    )


    companion object {
        const val CACHE_PATH_IN_HOME = ".gradle/caches/modules-2/files-2.1/"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lib

        if (pathA != other.pathA) return false

        return true
    }

    override fun hashCode(): Int {
        return pathA.hashCode()
    }

    override fun toString(): String {
        return "Lib($pathA)"
    }


}