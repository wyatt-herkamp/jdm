package me.kingtux.jdm.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.kingtux.jdm.common.*;
import me.kingtux.jdm.lib.classpath.ClassPathController;
import me.kingtux.mavenlibrary.ArtifactResolver;
import me.kingtux.mavenlibrary.Repository;
import me.kingtux.mavenlibrary.releases.ArtifactFile;
import me.kingtux.mavenlibrary.releases.ArtifactRelease;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DependManager {
    private static Gson gson;
    public ClassPathController classPathController;
    private File dependsFolder;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public DependManager(ClassPathController classPathController, File dependsFolder) {
        this.classPathController = classPathController;
        this.dependsFolder = dependsFolder;
        if (!dependsFolder.exists()) dependsFolder.mkdirs();
    }

    public DependManager(ClassPathController classPathController) {
        this(classPathController, new File("libs"));
    }

    static {
        gson = new GsonBuilder().registerTypeAdapter(JDMArtifact.class, new ArtifactSerializer()).registerTypeAdapter(Repository.class, new RepositorySerializer()).registerTypeAdapter(Report.class, new ReportSerializer()).create();
    }

    public Future<Report> downloadDependenciesAsync(Report report) {
       return executor.submit(() -> {
           ArtifactResolver resolver = new ArtifactResolver(report.getRepositoryList());
           //TODO add the machines local .m2 folder
           for (JDMArtifact jdmArtifact : report.getArtifactList()) {
               File archiveFile = generateLibPath(jdmArtifact);
               if (!archiveFile.exists()) {
                   ArtifactRelease artifactRelease = jdmArtifact.toMavenLibraryArtifact().resolveRelease(resolver);
                   ArtifactFile jar = artifactRelease.getArtifactFile("jar");
                   try {
                       jar.download(archiveFile);
                   } catch (IOException e) {
                       //TODO Catch for now later make the dev handle the errors
                       e.printStackTrace();
                   }
               }
               try {
                   classPathController.addToClassPath(archiveFile);
               } catch (ClassPathController.ClassPathControllerException e) {
                   //TODO Catch for now later make the dev handle the errors
                   e.printStackTrace();
               }
           }
           return report;
       });


    }

    private File generateLibPath(JDMArtifact jdmArtifact) {
        String folderPath = jdmArtifact.getGroupID() + File.separator + jdmArtifact.getArtifactID() + File.separator;
        File file = new File(dependsFolder, folderPath);
        if (!file.exists()) file.mkdirs();
        String fileName = jdmArtifact.getArtifactID() + "-" + jdmArtifact.getVersion() + ".jar";
        return new File(folderPath, fileName);
    }

    public Future<Report> downloadDependenciesAsync(InputStream inputStream) {
        try {
            return downloadDependenciesAsync(gson.fromJson(new InputStreamReader(inputStream, "UTF-8"), Report.class));
        } catch (UnsupportedEncodingException e) {
            //TODO Catch for now later make the dev handle the errors
            e.printStackTrace();
        }
        return null;
    }

    public Future<Report> downloadDependenciesAsync(Class<?> currentClass) {
        return downloadDependenciesAsync(currentClass.getResourceAsStream("/depends.json"));
    }

    public Future<Report> downloadDependenciesAsync() {
        return downloadDependenciesAsync(DependManager.class);
    }
}
