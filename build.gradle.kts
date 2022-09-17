import io.franzbecker.gradle.lombok.task.DelombokTask

plugins {
    idea
    java
    id("gg.essential.loom") version "0.10.0.+"
    id("dev.architectury.architectury-pack200") version "0.1.3"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
    id("io.github.juuxel.loom-quiltflower") version "1.7.3"
    `maven-publish`
}

group = "moe.nea"
version = "1.0.0"

// Toolchains:
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
    withJavadocJar()
}

// Minecraft configuration:
loom {
    log4jConfigs.from(file("log4j2.xml"))
    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
    }
}

sourceSets.main {
    output.setResourcesDir(file("$buildDir/classes/java/main"))
}

// Dependencies:

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")
    api("io.github.juuxel:libninepatch:1.1.0")
}

lombok {
    version = "1.18.22"
    sha256 = ""
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

val delombok by tasks.registering(DelombokTask::class) {
    mainClass.set(lombok.main)
    dependsOn(tasks.compileJava)
    val outputDir by extra { layout.buildDirectory.dir("delombok").get() }
    outputs.dir(outputDir)
    sourceSets["main"].java.srcDirs.forEach {
        inputs.dir(it)
        args(it, "-d", outputDir)
    }
    doLast {
        delete(outputDir.dir("moe/nea/molecula/test"))
    }
    doFirst {
        delete(outputDir)
    }
}

tasks.jar {
    exclude("moe/nea/molecula/test/*")
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(delombok)
    from(delombok)
    archiveClassifier.set("sources")
}
tasks.javadoc {
    isFailOnError = false
    dependsOn(delombok)
    val outputDir: Directory by delombok.get().extra
    source = outputDir.asFileTree
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(sourcesJar) {
                classifier = "sources"
            }
            artifact(tasks.remapJar)
            artifact(tasks.jar) {
                classifier = "deobf"
            }
            pom {
                name.set("Molecula")
                description.set("Library for creating vanilla-esque GUIs in Minecraft 1.8.9")
                licenses {
                    license {
                        name.set("BSD 3-Clause")
                        url.set("https://github.com/NotEnoughUpdates/RepoParser/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        name.set("Linnea Gräf")
                    }
                }
            }
        }
    }
}

