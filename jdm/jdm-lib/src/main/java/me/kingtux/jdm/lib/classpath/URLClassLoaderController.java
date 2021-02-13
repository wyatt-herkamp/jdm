package me.kingtux.jdm.lib.classpath;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * The ClassPathController for a URLClassLoader.
 * This is also compatible with Bukkit.
 */
public class URLClassLoaderController implements ClassPathController {
    private URLClassLoader urlClassLoader;
    private Method addURLMethod;

    public URLClassLoaderController(URLClassLoader urlClassLoader) throws NoSuchMethodException {
        this.urlClassLoader = urlClassLoader;
        addURLMethod = urlClassLoader.getClass().getMethod("addURL", URL.class);
    }

    @Override
    public void addToClassPath(File file) throws ClassPathControllerException {
        try {
            addURLMethod.invoke(urlClassLoader, file.toURI().toURL());
        } catch (IllegalAccessException | InvocationTargetException | MalformedURLException e) {
            throw new ClassPathControllerException(file, e);
        }
    }
}
