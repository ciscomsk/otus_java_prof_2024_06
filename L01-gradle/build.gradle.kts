plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.guava)
}

tasks {
    shadowJar {
//        archiveBaseName.set("gradleHelloWorld")
//        archiveVersion.set("0.1")
//        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.App"))
        }
    }

//    build {
//        dependsOn(shadowJar)
//    }
}