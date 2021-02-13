package me.kingtux.jdm.lib.classpath;

import java.io.File;

public interface ClassPathController {

    void addToClassPath(File file) throws ClassPathControllerException;

    class ClassPathControllerException extends Exception {
        public ClassPathControllerException(File file, Exception cause) {
            super(String.format("Unable to add %s to class path", file.getAbsolutePath()), cause);
        }
    }
}
