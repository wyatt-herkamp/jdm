package me.kingtux.jdm.gradle.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.kingtux.jdm.common.*;
import me.kingtux.jdm.gradle.JDMExtension;
import me.kingtux.mavenlibrary.Repository;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.jvm.tasks.Jar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class GenDependenciesTask {
    private JDMExtension jdmExtension;
    private Configuration jdmConfig;
    private Configuration jdmInternal;

    public GenDependenciesTask(JDMExtension jdmExtension, Configuration jdmConfig, Configuration jdmInternal) {
        this.jdmExtension = jdmExtension;
        this.jdmConfig = jdmConfig;
        this.jdmInternal = jdmInternal;
    }

    public void process(Project project) {
        List<JDMArtifact> artifactList = new ArrayList<>();
        for (Dependency dependency : jdmConfig.getDependencies()) {
            artifactList.add(new JDMArtifact(dependency.getGroup(), dependency.getName(), dependency.getVersion()));
        }
        List<Repository> repositoryList = new ArrayList<>();
        for (ArtifactRepository repository : project.getRepositories()) {
            if (repository instanceof MavenArtifactRepository) {
                MavenArtifactRepository repository1 = (MavenArtifactRepository) repository;
                repositoryList.add(new Repository(repository1.getUrl().toASCIIString(), repository.getName()));
            }
        }
        Report report = new Report(repositoryList, artifactList);
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(JDMArtifact.class, new ArtifactSerializer()).registerTypeAdapter(Repository.class, new RepositorySerializer()).registerTypeAdapter(Report.class, new ReportSerializer()).create();
        File file = new File(project.getBuildDir().getAbsolutePath() + File.separator + "resources" + File.separator + "main");
        if (!file.exists()) file.mkdirs();
        File depends = new File(file, "depends.json");
        if (!depends.exists()) depends.delete();
        try (FileWriter writer = new FileWriter(depends, Charset.forName("UTF-8"))) {

            gson.toJson(report, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bundleDepends(project);
    }

    public void bundleDepends(Project project) {
        Jar jar = (Jar) project.getTasks().getByName("jar");
        List<Object> zipTrees = new ArrayList<>();

        for (File file : jdmInternal) {
            System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            if (file.isDirectory()) {
                zipTrees.add(file);
            } else {
                zipTrees.add(project.zipTree(file));
            }
        }

        jar.from(zipTrees.toArray());
    }
}
