package org.jetbrains.kotlin.infra.jps

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

  var replaced = 0

  artifactsDir.listFiles()?.forEach { xmlFile ->
    if (xmlFile.isFile && !xmlFile.name.startsWith("dist_auto_")) {
      var txt = xmlFile.readText()
      libs.forEach { lib ->
        txt = lib.regex.replace(txt) {
          val srcValue = it.value.removeSurrounding("\"")
          if (srcValue != lib.path) {
            val old = Lib(srcValue)
            if (old.configuration == lib.configuration) {
              println("${xmlFile.relativeTo(artifactsDir)} replacing: ${lib.group}:${lib.artifact}:${old.version}:${lib.configuration} -> ${lib.version} [${old.hash} -> ${lib.hash}]")
              replaced++
              "\"${lib.path}\""
            } else it.value
          } else it.value
        }
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

private fun printUsage() {
  println("Usage: jps-update-bootstrap /path/to/kotlin-project")
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