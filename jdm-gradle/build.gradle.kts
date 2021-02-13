plugins {
    id("java")
    id("java-library")
    id("com.gradle.plugin-publish") version "0.12.0"
    id("java-gradle-plugin")
}

group = "me.kingtux"
version = "1.0.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    jcenter()
    maven("https://repo.potatocorp.dev/storages/maven/kingtux-repo")

}
dependencies {
    compileOnly(group = "me.kingtux", name = "jdm-common", version = "1.0.0-SNAPSHOT")
    compileOnly("com.google.code.gson:gson:2.8.6")
    compileOnly(group = "me.kingtux", name = "mavenlibrary", version = "1.0-SNAPSHOT")
}
gradlePlugin {
    plugins {
        create("me.kingtux.jdm.jdm-gradle") {
            id = "me.kingtux.jdm.jdm-gradle"
            implementationClass = "me.kingtux.jdm.gradle.JDMPlugin"
            version = "1.0.0-SNAPSHOT"
        }
    }
}

pluginBundle {
    vcsUrl = "https://github.com/wherkamp/JDM"
    website = "https://kingtux.dev/JDM"
    description = "Generates a Version File from your source"
    tags = listOf("depends")

    plugins {
        getByName("me.kingtux.jdm.jdm-gradle") {
            displayName = "JDM"
        }
    }
}
buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.gradle.publish:plugin-publish-plugin:0.12.0")
    }
}
tasks {
    jar {
        from(
            *configurations.compileOnly.get().map { if (it.isDirectory) it else zipTree(it) }.toTypedArray()
        )
    }
}

apply(plugin = "com.gradle.plugin-publish")