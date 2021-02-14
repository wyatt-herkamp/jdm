plugins {
    java
}

group = "me.kingtux"
version = "1.0-SNAPSHOT"

tasks.register<Javadoc>("aggregatedJavadocs") {
    setDestinationDir(file("$buildDir/docs/javadoc"))
    title = "$project.name $version API"
    options.withGroovyBuilder {
        "author"(true)
        "addStringOption"("Xdoclint:none", "-quiet")
        "addStringOption"("sourcepath", "")
    }
    subprojects.forEach { proj ->
        proj.tasks.filterIsInstance<Javadoc>().forEach {
            source += it.source
            classpath += it.classpath
            excludes += it.excludes
            includes += it.includes
        }
    }
}