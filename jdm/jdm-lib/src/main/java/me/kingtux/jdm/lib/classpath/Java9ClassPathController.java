package me.kingtux.jdm.lib.classpath;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

/**
 * <b>Before asking how do you use this please read https://dzone.com/articles/java-agent-1</b>
 * This is an implementation of the ClassPathController for Java 9+ using the Instrumentation class in Java.
 */
public class Java9ClassPathController implements ClassPathController {
    private Instrumentation instrumentation;

    public Java9ClassPathController(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public void addToClassPath(File file) throws ClassPathControllerException {
        try {
            instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(file));
        } catch (IOException e) {
            throw new ClassPathControllerException(file, e);
        }
    }
}
