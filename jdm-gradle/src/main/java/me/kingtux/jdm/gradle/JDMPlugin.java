package me.kingtux.jdm.gradle;

import me.kingtux.jdm.gradle.tasks.GenDependenciesTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

public class JDMPlugin implements Plugin<Project> {
    public static Configuration createJDMConfig(Project project) {
        project.getPlugins().apply("java");
        Configuration jdm = project.getConfigurations().create("jdm");
        Configuration compileOnly = project.getConfigurations().findByName("compileOnly");
        if (compileOnly == null) {
            throw new IllegalStateException("compileOnly not found. Requires the Java plugin to be present");
        }
        compileOnly.extendsFrom(jdm);
        return jdm;
    }

    public static Configuration addJDMDepends(Project project) {
        Configuration jdmInternal = project.getConfigurations().create("JDMInternal");
        Configuration implementation =project.getConfigurations().getByName("implementation");

        implementation.extendsFrom(jdmInternal);


        return jdmInternal;
    }

    @Override
    public void apply(Project target) {

        JDMExtension extension = (JDMExtension) target.getExtensions().create("jdm", JDMExtension.class);
        GenDependenciesTask genDependenciesTask = new GenDependenciesTask(extension, createJDMConfig(target), addJDMDepends(target));
        target.getTasks().register("jdm").getOrNull().doLast(task -> {
            genDependenciesTask.process(target);
        });
    }

}
