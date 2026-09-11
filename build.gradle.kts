plugins {
    kotlin("jvm") version "2.2.0"
    application
}

group = "com.oremoschanganaptcli"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:5.0.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.oremoschanganaptcli.MainKt")
}