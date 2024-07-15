import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
// alias(libs.plugins.spring.boot)
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {
    alias(libs.plugins.dependency.management)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.sonarlint)
    alias(libs.plugins.spotless)
    alias(libs.plugins.jgitver)
}

allprojects {
    repositories {
        mavenCentral()
//        mavenLocal()
    }

    val protobufBom: String by project
    val testcontainersBom: String by project
    val guava: String by project

    // alias(libs.plugins.dependency.management)
    // *2 - в subprojects показывает не все зависимости
    pluginManager.apply("io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
                mavenBom("com.google.protobuf:protobuf-bom:$protobufBom")
                mavenBom("org.testcontainers:testcontainers-bom:$testcontainersBom")
            }
            // описываем зависимости, отсутствующие в BOM
            dependency("com.google.guava:guava:$guava")
        }
    }
}

subprojects {
    group = "ru.otus"
    version = "1.0-SNAPSHOT"

    // переехало в allprojects - *1
//    repositories {
//        mavenCentral()
////        mavenLocal()
//    }

    pluginManager.apply("java")
    // =
//    apply(plugin = "java")
    // =
//    pluginManager.apply(JavaPlugin::class.java)
    // =
//    plugins.apply(JavaPlugin::class.java)

    // alias(libs.plugins.sonarlint)
    pluginManager.apply("name.remal.sonarlint")
    // =
//    apply<name.remal.gradle_plugins.sonarlint.SonarLintPlugin>()

    // alias(libs.plugins.spotless)
    pluginManager.apply("com.diffplug.spotless")
    // extensions.configure = configure
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            palantirJavaFormat("2.47.0")
        }
    }

    pluginManager.apply("fr.brouillard.oss.gradle.jgitver")
    configure<fr.brouillard.oss.gradle.plugins.JGitverPluginExtension> {
        strategy("PATTERN")
        nonQualifierBranches("main,master")
        tagVersionPattern("\${v}\${<meta.DIRTY_TEXT}")
        versionPattern(
            "\${v}\${<meta.COMMIT_DISTANCE}\${<meta.GIT_SHA1_8}" +
                    "\${<meta.QUALIFIED_BRANCH_NAME}\${<meta.DIRTY_TEXT}-SNAPSHOT"
        )
    }

    // переехало в allprojects - *2
//    val protobufBom: String by project
//    val testcontainersBom: String by project
//    val guava: String by project
//
    // alias(libs.plugins.dependency.management)
//    pluginManager.apply("io.spring.dependency-management")
//    dependencyManagement {
//        dependencies {
//            imports {
//                mavenBom(BOM_COORDINATES)
//                mavenBom("com.google.protobuf:protobuf-bom:$protobufBom")
//                mavenBom("org.testcontainers:testcontainers-bom:$testcontainersBom")
//            }
//            // описываем зависимости, отсутствующие в BOM
//            dependency("com.google.guava:guava:$guava")
//        }
//    }

    configurations.all {
        resolutionStrategy {
            failOnVersionConflict()

            force("com.google.code.findbugs:jsr305:3.0.2")
            force("commons-io:commons-io:2.15.1")
            force("org.sonarsource.analyzer-commons:sonar-analyzer-commons:2.11.0.2861")
            force("org.sonarsource.analyzer-commons:sonar-analyzer-recognizers:2.11.0.2861")
            force("org.sonarsource.analyzer-commons:sonar-xml-parsing:2.11.0.2861")
            force("org.sonarsource.sslr:sslr-core:1.24.0.633")
        }
    }

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:all"))
    }
}

// вывод версий всех зависимостей из dependencyManagement
// *1 - работает только при указании repositories в allprojects
tasks {
    val managedVersions by registering {
        doLast {
            project.extensions.getByType<DependencyManagementExtension>()
                .managedVersions
                .toSortedMap()
                .map { "${it.key}:${it.value}" }
                .forEach(::println)
        }
    }
}
