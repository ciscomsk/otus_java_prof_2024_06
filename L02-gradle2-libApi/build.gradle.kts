plugins {
    id("java-library")
}

dependencies {
    implementation(libs.logback)

    // для работы api нужен плагин java-library
    api(libs.guava)
//    implementation(libs.guava)
}
