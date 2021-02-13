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

}
jdm {


}
tasks {
    "jar"{
        dependsOn(project.tasks.getByName("jdm"))
    }


}
