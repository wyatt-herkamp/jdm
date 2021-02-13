rootProject.name = "jbm-lib"
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://repo.potatocorp.dev/storages/maven/kingtux-repo")
    }
}
include("jdm-common")
include("jdm-lib")

