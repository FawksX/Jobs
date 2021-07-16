import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.gamingmesh"
version = "4.17.2"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {

    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") } // PaperMC
    maven { url = uri("https://mvn.lumine.io/repository/maven-public/") } // MythicMobs
    maven { url = uri("https://maven.enginehub.org/repo/") } // WorldGuard / WorldEdit
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://libraries.minecraft.net/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://repo.incendo.org/content/repositories/snapshots") }

}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.21")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("io.lumine.xikage:MythicMobs:4.9.1")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.0")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.1.0-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.9")

    compileOnly(fileTree("libs"))

    implementation("org.spongepowered:configurate-yaml:4.0.0")
    implementation("cloud.commandframework:cloud-paper:1.5.0-SNAPSHOT")
    implementation("cloud.commandframework:cloud-annotations:1.5.0-SNAPSHOT")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.4.0") {
        exclude("net.kyori")
    }
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT") {
        exclude("adventure-api")
    }
    implementation("net.kyori:adventure-text-feature-pagination:4.0.0-SNAPSHOT") {
        exclude("adventure-api")
    }
    implementation("com.zaxxer:HikariCP:4.0.3") {
        exclude("org.slf4j")
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shaded")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.gamingmesh.jobs.Jobs"))
        }

        val PREFIX = "com.gamingmesh.jobs."

        relocate("cloud.commandframework", PREFIX + "cloud")
        relocate("com.zaxxer.hikari", PREFIX + "hikari")
    }
    named<Copy>("processResources") {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
