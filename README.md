Tool for updating idea artifacts configuration for Kotlin JPS build.

1. Update bootstrap in `build.gradle.kts`
2. Open or sync project in IDEA with jpsBuild enabled https://jetbrains.quip.com/ynohAbVMwQWT/Building-Kotlin-via-JPS
3. Close IDEA (or don't get it focus even on commit since changed project files may be overridden by idea)
4. Download tool from https://github.com/snrostov/jps-artifacts-update-bootstrap/releases
5. Run `jps-artifacts-update-bootstrap /path/to/your/kotlin-project-checkout`
6. Commit changed files
7. Run tool again to check everything is up to date

Sample commit: https://github.com/JetBrains/kotlin/commit/6c96946937823fc26a5b09decd468ae61e56218c

Sample output:

```
> jps-artifacts-update-bootstrap /Users/jetbrains/tasks/jps
Updating gradle cache references in project /Users/jetbrains/tasks/jps
Artifacts dir: /Users/jetbrains/tasks/jps/.idea/artifacts
dist.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-minimal-for-test:1.3.30-dev-1945: -> 1.3.40-dev-431 [fd51bf7cb7a2f99efe9b0e6435acf2dfe43f34b2 -> 27afe096092320e7eb93f7916dd8c9a5c468f755]
dist.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-common:1.3.30-dev-1945: -> 1.3.40-dev-431 [547b60cf86ed0355a2312093d200f503ce9803f3 -> 47aa2315833376b606853f7e5a04c43098dc6b53]
dist.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-common:1.3.30-dev-1945:-sources -> 1.3.40-dev-431 [37fb3685709b0eca5dccc1a7b12ab5bebdcbe316 -> a725231829fc4b06cee89e7ca358a6f95ad1fca6]
dist.xml replacing: org.jetbrains.kotlin:kotlin-stdlib:1.3.30-dev-1945:-sources -> 1.3.40-dev-431 [af8320f3d13a7ab373e7f29f63b7749aa938ee54 -> 5c1350c7bf170f54defb614bde07cc87d83646cb]
dist.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-js:1.3.30-dev-1945: -> 1.3.40-dev-431 [9343ff4e085b20921479da5c3125c379986c84df -> 57a6e812c9f7da86e88567ceea35a65be3ee464e]
kotlin_compiler_jar.xml replacing: org.jetbrains.kotlin:builtins:1.3.30-dev-1945: -> 1.3.40-dev-431 [f5c03880d3dc304ef5dacc511310e12dac983ba6 -> 8b10c0a561c923af643379d168ba6178655addb0]
ideaPlugin.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-common:1.3.40-dev-431: -> 1.3.40-dev-431 [47aa2315833376b606853f7e5a04c43098dc6b53 -> 47aa2315833376b606853f7e5a04c43098dc6b53]
ideaPlugin.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.40-dev-431: -> 1.3.40-dev-431 [d8a120266797db4748f18d1115c51a4d4096afa3 -> d8a120266797db4748f18d1115c51a4d4096afa3]
ideaPlugin.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.40-dev-431: -> 1.3.40-dev-431 [f88c5f19731d030c55fac66784666ee86ef8b065 -> f88c5f19731d030c55fac66784666ee86ef8b065]
ideaPlugin.xml replacing: org.jetbrains.kotlin:kotlin-stdlib:1.3.40-dev-431: -> 1.3.40-dev-431 [909b3628ea6f0feb2731868860f208a306c16cab -> 909b3628ea6f0feb2731868860f208a306c16cab]
ideaPlugin.xml replacing: org.jetbrains:annotations:13.0: -> 13.0 [919f0dfe192fb4e063e7dacadee7f8bb9a2672a9 -> 919f0dfe192fb4e063e7dacadee7f8bb9a2672a9]
ideaPlugin.xml replacing: org.jetbrains.kotlin:builtins:1.3.40-dev-431: -> 1.3.40-dev-431 [8b10c0a561c923af643379d168ba6178655addb0 -> 8b10c0a561c923af643379d168ba6178655addb0]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.30-dev-1945: -> 1.3.40-dev-431 [a3bd59a6a1a46590da6af97c48935342031619df -> d8a120266797db4748f18d1115c51a4d4096afa3]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.30-dev-1945:-sources -> 1.3.40-dev-431 [abf5636e467ed2d993d6a1687c31fe0884441e7b -> 619e959f9f468e2f120b8646965e0d1df7b0ac7b]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.30-dev-1945: -> 1.3.40-dev-431 [ed140be9ff355822aafea93dba58bc2621ae6ad0 -> f88c5f19731d030c55fac66784666ee86ef8b065]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.30-dev-1945:-sources -> 1.3.40-dev-431 [540041ddf83c40fc1224ec124fdd45620ce32500 -> 363de43b4bd0c4fbfe6c54c182bbbc7a48a9b07]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib:1.3.30-dev-1945: -> 1.3.40-dev-431 [561b4a9c9bc7c799658e0b7b898b2861585a5cbf -> 909b3628ea6f0feb2731868860f208a306c16cab]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib:1.3.30-dev-1945:-sources -> 1.3.40-dev-431 [af8320f3d13a7ab373e7f29f63b7749aa938ee54 -> 5c1350c7bf170f54defb614bde07cc87d83646cb]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-js:1.3.30-dev-1945: -> 1.3.40-dev-431 [9343ff4e085b20921479da5c3125c379986c84df -> 57a6e812c9f7da86e88567ceea35a65be3ee464e]
kotlinc.xml replacing: org.jetbrains.kotlin:kotlin-stdlib-js:1.3.30-dev-1945:-sources -> 1.3.40-dev-431 [9428b7adafb24b8ac52180f5b831f5cafdd0285e -> 588b11ab856c3dbcaedf5aecd37a5eebdd909651]
kotlinc.xml replacing: org.jetbrains:annotations:13.0: -> 13.0 [919f0dfe192fb4e063e7dacadee7f8bb9a2672a9 -> 919f0dfe192fb4e063e7dacadee7f8bb9a2672a9]
Done. 21 references updated.
```

On up to date:

```
> jps-artifacts-update-bootstrap /Users/jetbrains/tasks/jps
Updating gradle cache references in project /Users/jetbrains/tasks/jps
Artifacts dir: /Users/jetbrains/tasks/jps/.idea/artifacts
Everything already up to date.
```