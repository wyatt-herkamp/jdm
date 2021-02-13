package me.kingtux.jdm.example;

import me.kingtux.jdm.lib.DependManager;
import me.kingtux.jdm.lib.classpath.ClassPathController;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("HEY");
        ClassPathController testClassPathController = new TestClassPathController();
        DependManager dependManager = new DependManager(testClassPathController);
        dependManager.downloadDependenciesAsync().get();
        System.exit(1);
    }

    private static class TestClassPathController implements ClassPathController {

        @Override
        public void addToClassPath(File file) throws ClassPathControllerException {
            System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        }
    }
}
