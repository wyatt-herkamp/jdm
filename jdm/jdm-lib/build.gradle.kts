plugins {
    id("java")
    id("java-library")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    `maven-publish`

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
    implementation(group = "me.kingtux", name = "jdm-common", version = "1.0.0-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation(group = "me.kingtux", name = "mavenlibrary", version = "1.0-SNAPSHOT")
}
val artifactName = "jdm-lib"
java {
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {

        create<MavenPublication>("mavenJava") {
            artifact(tasks["shadowJar"])

            artifactId = artifactName
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(artifactName)
            }
        }
    }
    repositories {
        maven {

            val releasesRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            val snapshotsRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)

        }
        mavenLocal()
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveClassifier.set("")

    }
    "jar"{
        enabled = false
    }

    "assemble"{
        dependsOn(shadowJar)

    }
}
