package org.jetbrains.kotlin.infra.jps

import org.junit.Assert.*
import org.junit.Test
import java.lang.StringBuilder

class LibTest {
    @Test
    fun testReference() {
        val lib = Lib("\"\$USER_HOME\$/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-minimal-for-test/1.3.30-dev-1419/3fc425e5a7e1979aad08d0f420f07c19c9c36d63/kotlin-stdlib-minimal-for-test-1.3.30-dev-1419.jar\"")
        assertEquals(
            "Lib(org.jetbrains.kotlin/kotlin-stdlib-minimal-for-test/1.3.30-dev-1419/3fc425e5a7e1979aad08d0f420f07c19c9c36d63/kotlin-stdlib-minimal-for-test-1.3.30-dev-1419.jar)",
            lib.toString()
        )

        val reports = StringBuilder()
        val r = object: Replacer() {
            override fun report(msg: String) {
                reports.appendln(msg)
            }
        }

        val result = r.replace(lib, """
<component name="ArtifactManager">
  <artifact build-on-make="true" name="dist">
    <output-path>${'$'}PROJECT_DIR${'$'}/dist</output-path>
    <root id="root">
      <element id="directory" name="artifacts">
        <element id="directory" name="ideaPlugin">
          <element id="artifact" artifact-name="ideaPlugin" />
        </element>
      </element>
      <element id="file-copy" path="${'$'}PROJECT_DIR${'$'}/build/build.txt" />
      <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-minimal-for-test/1.3.50-dev-455/8b1ab64efeb4c2030834c9f7c2f09e13e8ef9230/kotlin-stdlib-minimal-for-test-1.3.50-dev-455.jar" output-file-name="kotlin-stdlib-minimal-for-test.jar" />
      <element id="directory" name="common">
        <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.3.50-dev-455/c1e5eaac0191584f9da494ac8e48626f8463e6a/kotlin-stdlib-common-1.3.50-dev-455.jar" output-file-name="kotlin-stdlib-common.jar" />
        <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.3.50-dev-455/51e07839d3876992c49979b893067efb9dc41656/kotlin-stdlib-common-1.3.50-dev-455-sources.jar" output-file-name="kotlin-stdlib-common-sources.jar" />
      </element>
      <element id="directory" name="js">
        <element id="extracted-dir" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-js/1.3.50-dev-455/cb005c5615b58cad42814fe0b0ba606289b1dcfa/kotlin-stdlib-js-1.3.50-dev-455.jar" path-in-jar="/" />
      </element>
      <element id="directory" name="maven">
        <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.3.50-dev-455/28ef2b45c6356183179b6e5e4c354a784aaf734/kotlin-stdlib-1.3.50-dev-455-sources.jar" output-file-name="kotlin-stdlib-sources.jar" />
      </element>
      <element id="directory" name="kotlinc">
        <element id="artifact" artifact-name="kotlinc" />
      </element>
    </root>
  </artifact>
</component>
        """.trimIndent(), "PATH")

        assertEquals(
            "PATH replacing: org.jetbrains.kotlin:kotlin-stdlib-minimal-for-test:1.3.50-dev-455: -> 1.3.30-dev-1419 [8b1ab64efeb4c2030834c9f7c2f09e13e8ef9230 -> 3fc425e5a7e1979aad08d0f420f07c19c9c36d63]",
            reports.toString().trim()
        )

        assertEquals("""<component name="ArtifactManager">
  <artifact build-on-make="true" name="dist">
    <output-path>${'$'}PROJECT_DIR${'$'}/dist</output-path>
    <root id="root">
      <element id="directory" name="artifacts">
        <element id="directory" name="ideaPlugin">
          <element id="artifact" artifact-name="ideaPlugin" />
        </element>
      </element>
      <element id="file-copy" path="${'$'}PROJECT_DIR${'$'}/build/build.txt" />
      <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-minimal-for-test/1.3.30-dev-1419/3fc425e5a7e1979aad08d0f420f07c19c9c36d63/kotlin-stdlib-minimal-for-test-1.3.30-dev-1419.jar" output-file-name="kotlin-stdlib-minimal-for-test.jar" />
      <element id="directory" name="common">
        <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.3.50-dev-455/c1e5eaac0191584f9da494ac8e48626f8463e6a/kotlin-stdlib-common-1.3.50-dev-455.jar" output-file-name="kotlin-stdlib-common.jar" />
        <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.3.50-dev-455/51e07839d3876992c49979b893067efb9dc41656/kotlin-stdlib-common-1.3.50-dev-455-sources.jar" output-file-name="kotlin-stdlib-common-sources.jar" />
      </element>
      <element id="directory" name="js">
        <element id="extracted-dir" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-js/1.3.50-dev-455/cb005c5615b58cad42814fe0b0ba606289b1dcfa/kotlin-stdlib-js-1.3.50-dev-455.jar" path-in-jar="/" />
      </element>
      <element id="directory" name="maven">
        <element id="file-copy" path="${'$'}USER_HOME${'$'}/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.3.50-dev-455/28ef2b45c6356183179b6e5e4c354a784aaf734/kotlin-stdlib-1.3.50-dev-455-sources.jar" output-file-name="kotlin-stdlib-sources.jar" />
      </element>
      <element id="directory" name="kotlinc">
        <element id="artifact" artifact-name="kotlinc" />
      </element>
    </root>
  </artifact>
</component>""", result)
    }
}