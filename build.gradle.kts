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

tasks.register("packageDeb") {
    dependsOn("linkReleaseExecutableLinuxX64")

    doLast {
        val debDir = layout.buildDirectory.dir("deb/oremos-cli").get().asFile
        val binDir = File(debDir, "usr/bin")
        val controlDir = File(debDir, "DEBIAN")

        binDir.mkdirs()
        controlDir.mkdirs()

        val executable = layout.buildDirectory
            .file("bin/linuxX64/releaseExecutable/oremos-cli.kexe")
            .get()
            .asFile

        val target = File(binDir, "oremos-cli")

        executable.copyTo(target, overwrite = true)

        target.setExecutable(true)

        File(controlDir, "control").writeText(
            """
        Package: oremos-cli
        Version: $version
        Section: utils
        Priority: optional
        Architecture: amd64
        Maintainer: Samuel Sumbane
        Description: Oremos Changana-PT CLI
         CLI para consulta de orações e cânticos em Changana e Português.
        """.trimIndent() + "\n"
        )

        controlDir.setExecutable(true)

        val output = layout.buildDirectory
            .file("deb/oremos-cli_${version}_amd64.deb")
            .get()
            .asFile

        exec {
            commandLine(
                "dpkg-deb",
                "--build",
                debDir.absolutePath,
                output.absolutePath
            )
        }
    }
}

tasks.register("packageWindows") {
    dependsOn("linkReleaseExecutableMingwX64")

    doLast {
        val executable = layout.buildDirectory
            .file("bin/mingwX64/releaseExecutable/oremos-cli.exe")
            .get()
            .asFile

        val outputDir = layout.buildDirectory
            .dir("windows")
            .get()
            .asFile

        outputDir.mkdirs()

        val output = File(
            outputDir,
            "oremos-cli-${version}-windows-x64.exe"
        )

        executable.copyTo(output, overwrite = true)

        println("Windows executable: ${output.absolutePath}")
    }
}