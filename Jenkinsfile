pipeline {
    agent any

    parameters {
        choice(
            name: 'APP_ENV',
            choices: ['development', 'production'],
            description: 'Application environment for deployment'
        )
    }

    environment {
        TOMCAT_HOME = '/opt/homebrew/opt/tomcat@10/libexec'
        WAR_NAME = 'automated-grocery-inventory-dashboard.war'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh '''
                    export JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home
                    export PATH="$JAVA_HOME/bin:$PATH"

                    java -version
                    chmod +x mvnw

                    ./mvnw clean test
                '''
            }
        }

        stage('Package') {
            steps {
                sh '''
                    export JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home
                    export PATH="$JAVA_HOME/bin:$PATH"

                    ./mvnw package -DskipTests

                    ls -lh target/*.war
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    echo "Deploying application environment: ${APP_ENV}"

                    cp target/*.war \
                    "$TOMCAT_HOME/webapps/$WAR_NAME"

                    echo "WAR deployed to Tomcat."
                    echo "Application URL:"
                    echo "http://localhost:8082/automated-grocery-inventory-dashboard/items"
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }

        failure {
            echo 'Pipeline failed. Check the stage logs.'
        }
    }
}