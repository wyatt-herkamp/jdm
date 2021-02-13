plugins {
    id("java")
    id("java-library")
    id("me.kingtux.jdm.jdm-gradle") version "1.0.0-SNAPSHOT"

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
    jdm(group = "me.kingtux", name = "mavenlibrary", version = "1.0-SNAPSHOT")
    jdm("com.google.code.gson:gson:2.8.6")
    JDMInternal(group = "me.kingtux", name = "jdm-common", version = "1.0.0-SNAPSHOT")
    JDMInternal(group = "me.kingtux", name = "jdm-lib", version = "1.0.0-SNAPSHOT")


}

jdm {


}
tasks.withType<Jar> {
    // Otherwise you'll get a "No main manifest attribute" error
    manifest {
        attributes["Main-Class"] = "me.kingtux.jdm.example.Main"
    }

}
tasks {
    "jar"{
        dependsOn(project.tasks.getByName("jdm"))

    }


}
