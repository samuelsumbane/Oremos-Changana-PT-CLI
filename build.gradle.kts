plugins {
    kotlin("multiplatform") version "2.2.0"
//    application
}

group = "com.oremoschanganaptcli"
version = "1.0.0"

repositories {
    mavenCentral()
}

/*dependencies {
    implementation("com.github.ajalt.clikt:clikt:5.1.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}*/

/*
application {
    mainClass.set("com.oremoschanganaptcli.MainKt")
}*/


kotlin {
    linuxX64 {
        binaries {
            executable {
                baseName = "oremos-cli"
                entryPoint = "com.oremoschanganaptcli.main"
            }
        }
    }

    mingwX64 {
        binaries {
            executable {
                baseName = "oremos-cli"
                entryPoint = "com.oremoschanganaptcli.main"
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("com.github.ajalt.clikt:clikt:5.0.3")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}