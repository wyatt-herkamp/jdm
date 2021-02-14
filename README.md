# JDM

The Java Dependency Manager

## How to use?

The example module has a simple example for using the library.

```
//Plugins
    id("me.kingtux.jdm.jdm-gradle") version "1.0.0-SNAPSHOT"
//Tasks
tasks {
    "jar"{
        dependsOn(project.tasks.getByName("jdm"))
    }
}
//Depends
dependencies {
    //Depends you want to load via the JDM loader
    jdm(group = "me.kingtux", name = "mavenlibrary", version = "1.0-SNAPSHOT")
    jdm("com.google.code.gson:gson:2.8.6")
    //depends you want to be shaded in. These two are required
    JDMInternal(group = "me.kingtux", name = "jdm-common", version = "1.0.0-SNAPSHOT")
    JDMInternal(group = "me.kingtux", name = "jdm-lib", version = "1.0.0-SNAPSHOT")
}
```

In your project

```java
class Main {
    public static void main(String[] args) {
        //The ClassPathController is way for JDM to know how you want to add your your depends into the class path.
        //
        ClassPathController testClassPathController = new TestClassPathController();
        DependManager dependManager = new DependManager(testClassPathController);
        dependManager.downloadDependenciesAsync().get();
    }
}
```