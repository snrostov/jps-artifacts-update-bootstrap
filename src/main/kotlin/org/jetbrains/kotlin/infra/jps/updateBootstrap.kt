package org.jetbrains.kotlin.infra.jps

import java.io.File

fun main() {
    val projectDir = File("/Users/jetbrains/tasks/kwjps/wjps")
    val artifactsDir = projectDir.resolve(".idea/artifacts")

    // "$USER_HOME$/.gradle/caches/modules-2/files-2.1/
    // org.jetbrains.kotlin/kotlin-stdlib-minimal-for-test/
    // 1.3.30-dev-1419/
    // 3fc425e5a7e1979aad08d0f420f07c19c9c36d63/
    // kotlin-stdlib-minimal-for-test-1.3.30-dev-1419.jar"

    val ref = artifactsDir.resolve("dist_auto_stdlib_reference_dont_use.xml").readText()
    val libs = mutableListOf<Lib>()
    ref.lines().forEach {
        val a = it.trim()
        val prefix = "<element id=\"file-copy\" path=\""
        if (a.startsWith(prefix)) {
            val path = a.removePrefix(prefix).removeSuffix("\" />")
            libs.add(Lib(path))
        }
    }

    artifactsDir.listFiles()?.forEach { xmlFile ->
        if (xmlFile.isFile && !xmlFile.name.startsWith("dist_auto_")) {
            println("----------- ${xmlFile.name}")
            var txt = xmlFile.readText()
            libs.forEach { lib ->
                txt = lib.regex.replace(txt) {
                    val old = Lib(it.value.removeSurrounding("\""))
                    if (old.configuration == lib.configuration) {
                        println("${lib.group}:${lib.artifact}:${old.version}:${lib.configuration} -> ${lib.version} [${old.hash} -> ${lib.hash}]")

                        "\"${lib.path}\""
                    } else {
                        it.value
                    }
                }
            }
            xmlFile.writeText(txt)
        }
    }
}

data class Lib(val path: String) {
    val parts = path.split("/")
    val group = parts[5]
    val artifact = parts[6]
    val version = parts[7]
    val hash = parts[8]
    val filename = parts[9]
    val configuration = filename.removePrefix("$artifact-$version").removeSuffix(".jar")
    val regex = Regex("\"\\\$USER_HOME\\\$/.gradle/caches/modules-2/files-2.1/$group/$artifact/([^\"]+)\"")
}