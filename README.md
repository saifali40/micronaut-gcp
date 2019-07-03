# Micronaut Application on Google App Engine Standard with Java 11


#### Getting Start:

[Micronaut](https://www.baeldung.com/micronaut) is a JVM-based framework for building lightweight, modular applications. Developed by OCI, the same company that created Grails, Micronaut is the latest framework designed to make creating microservices quick and easy.
While Micronaut contains some features that are similar to existing frameworks like Spring, it also has some new features that set it apart. And with support for Java, Groovy, and Kotlin, it offers a variety of ways to create applications.

Please follow the installation setups for the micronaut application, I personally use the SDKMAN

and you can check the [micronaut](https://micronaut.io/) official documentation here.

#### what need to add after that?
add this to build script dependencies
```
        classpath 'com.google.cloud.tools:appengine-gradle-plugin:2.0.0-rc4'
```
so the output will be if you don't have any other dependencies.

###### Example :
```$xslt
buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath 'com.google.cloud.tools:appengine-gradle-plugin:2.0.0-rc4'
    }
}
```

Add this your plugin:

`apply plugin: 'com.google.cloud.tools.appengine'`

and finally for the appengine deployment
```$xslt
appengine {
    stage.artifact = "${buildDir}/libs/${project.name}-${project.version}-all.jar"
    deploy {
        projectId = 'projectId'
        version = 'versionName'
    }
}
```


##### App engine Configuration :

upto java8 the appengine configuration used to be in appengine-web.xml but there is slight change from 
Java 11 Aka second generation 
you need to add app.yml in the following directory

`src/main/appengine`

```
runtime: java11
instance_class: F2
```

as of now i tried with f1 instance. and it got broken, so i'm here using the f2 instance.

##### deploy to appengine
you can find the tasks which are available with app engine gradle sdk by running 

`gradle tasks`

you can use the following one to deploy the application only

`gradle appengineDeploy`


