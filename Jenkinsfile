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
        REGISTRY = 'localhost:5001'
        IMAGE_NAME = 'grocery-inventory-dashboard'
        CONTAINER_NAME = 'grocery-inventory-cd'
        HOST_PORT = '8083'
        CONTAINER_PORT = '8080'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh '''
                    export JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home
                    export PATH="$JAVA_HOME/bin:/usr/local/bin:/opt/homebrew/bin:$PATH"

                    java -version
                    docker --version

                    chmod +x mvnw
                    ./mvnw clean test
                '''
            }
        }

        stage('Package') {
            steps {
                sh '''
                    export JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home
                    export PATH="$JAVA_HOME/bin:/usr/local/bin:/opt/homebrew/bin:$PATH"

                    ./mvnw package -DskipTests

                    echo "Generated WAR:"
                    ls -lh target/*.war
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    export PATH="/usr/local/bin:/opt/homebrew/bin:$PATH"

                    echo "Building Docker image..."

                    docker build \
                        -t ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER} \
                        .

                    echo "Docker image created:"
                    docker images ${REGISTRY}/${IMAGE_NAME}
                '''
            }
        }

        stage('Push Image') {
            steps {
                sh '''
                    export PATH="/usr/local/bin:/opt/homebrew/bin:$PATH"

                    echo "Pushing image to local Docker registry..."

                    docker push \
                        ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}

                    echo "Image pushed successfully."
                '''
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                    export PATH="/usr/local/bin:/opt/homebrew/bin:$PATH"

                    echo "Deploying environment: ${APP_ENV}"
                    echo "Image: ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}"

                    echo "Stopping old container if it exists..."
                    docker stop ${CONTAINER_NAME} || true

                    echo "Removing old container if it exists..."
                    docker rm ${CONTAINER_NAME} || true

                    echo "Starting fresh container..."

                    docker run -d \
                        --name ${CONTAINER_NAME} \
                        -p ${HOST_PORT}:${CONTAINER_PORT} \
                        -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/grocery_inventory" \
                        -e SPRING_DATASOURCE_USERNAME="root" \
                        -e SPRING_DATASOURCE_PASSWORD="" \
                        ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}

                    echo "Container started."

                    echo "Running containers:"
                    docker ps

                    echo "Application URL:"
                    echo "http://localhost:${HOST_PORT}/automated-grocery-inventory-dashboard/items"
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    export PATH="/usr/local/bin:/opt/homebrew/bin:$PATH"

                    echo "Waiting for application startup..."
                    sleep 15

                    echo "Container status:"
                    docker ps --filter "name=${CONTAINER_NAME}"

                    echo "Container logs:"
                    docker logs --tail 30 ${CONTAINER_NAME}

                    echo "Registry tags:"
                    curl -s http://${REGISTRY}/v2/${IMAGE_NAME}/tags/list

                    echo ""
                    echo "Deployment completed successfully."
                '''
            }
        }
    }

    post {
        always {
            junit(
                testResults: 'target/surefire-reports/*.xml',
                allowEmptyResults: true
            )
        }

        success {
            echo "Week 12 Docker deployment completed successfully."
            echo "Image: ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}"
            echo "Environment: ${APP_ENV}"
            echo "Application: http://localhost:${HOST_PORT}/automated-grocery-inventory-dashboard/items"
        }

        failure {
            echo 'Pipeline failed. Docker deployment was not completed.'
        }
    }
}
