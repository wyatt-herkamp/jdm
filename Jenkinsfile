pipeline {
    agent any
    tools {
        jdk 'Java 11'
    }
    stages {
        stage ('Build') {
            steps {
              sh 'build.sh'
            }
            post {
                success {
                    javadoc javadocDir: 'jdm/build/docs/javadoc', keepAll: true
                }
            }
        }
    }
}