import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //application
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha3"

}
group = "me.ricardo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}
dependencies {
    implementation(compose.desktop.currentOs)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation(kotlin("test"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test { useJUnitPlatform() }