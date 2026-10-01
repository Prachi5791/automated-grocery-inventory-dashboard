pipeline {
    agent any

    environment {
        JAVA_HOME = '/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home'
        PATH = "/usr/local/bin:/opt/homebrew/bin:${env.PATH}"

        REGISTRY = 'localhost:5001'
        IMAGE_NAME = 'grocery-inventory-dashboard'
        CONTAINER_PORT = '8086'

        ANSIBLE_INVENTORY = 'ansible/inventory.ini'
        ANSIBLE_PLAYBOOK = 'ansible/week15/final-provision.yml'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Selenium Quality Gate') {
            steps {
                sh '''
                    echo "========================================"
                    echo "Running Selenium Quality Gate"
                    echo "========================================"

                    export JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home
                    export PATH="$JAVA_HOME/bin:/usr/local/bin:/opt/homebrew/bin:$PATH"

                    java -version
                    chmod +x mvnw

                    ./mvnw clean test
                '''
            }
        }

        stage('Package WAR') {
            steps {
                sh '''
                    echo "========================================"
                    echo "Packaging Application WAR"
                    echo "========================================"

                    export JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home
                    export PATH="$JAVA_HOME/bin:/usr/local/bin:/opt/homebrew/bin:$PATH"

                    ./mvnw package -DskipTests

                    ls -lh target/*.war
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    echo "========================================"
                    echo "Building Versioned Docker Image"
                    echo "========================================"

                    export PATH="/usr/local/bin:/opt/homebrew/bin:$PATH"

                    IMAGE_TAG="${BUILD_NUMBER}"
                    FULL_IMAGE="${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"

                    echo "Build number: ${BUILD_NUMBER}"
                    echo "Image: ${FULL_IMAGE}"

                    docker build \
                        -t "${FULL_IMAGE}" \
                        .

                    docker images "${FULL_IMAGE}"
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                sh '''
                    echo "========================================"
                    echo "Pushing Docker Image to Local Registry"
                    echo "========================================"

                    export PATH="/usr/local/bin:/opt/homebrew/bin:$PATH"

                    IMAGE_TAG="${BUILD_NUMBER}"
                    FULL_IMAGE="${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"

                    docker push "${FULL_IMAGE}"
                '''
            }
        }

        stage('Ansible Provision and Deploy') {
            steps {
                sh '''
                    echo "========================================"
                    echo "Ansible Provisioning and Deployment"
                    echo "========================================"

                    export PATH="/usr/local/bin:/opt/homebrew/bin:$PATH"

                    echo "Ansible version:"
                    ansible-playbook --version

                    echo "Deploying image:"
                    echo "${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}"

                    ansible-playbook \
                        -i "${ANSIBLE_INVENTORY}" \
                        "${ANSIBLE_PLAYBOOK}" \
                        -e "release_tag=${BUILD_NUMBER}"
                '''
            }
        }

        stage('Final Health Check') {
            steps {
                sh '''
                    echo "========================================"
                    echo "Final Application Health Check"
                    echo "========================================"

                    APP_URL="http://localhost:${CONTAINER_PORT}/automated-grocery-inventory-dashboard/items"

                    echo "Checking: ${APP_URL}"

                    curl --fail --silent --show-error \
                        --retry 5 \
                        --retry-delay 5 \
                        --output /dev/null \
                        "${APP_URL}"

                    echo "HTTP health check: PASSED"
                    echo "Application is responding successfully."
                '''
            }
        }
    }

    post {
        success {
            echo "========================================"
            echo "FINAL END-TO-END PIPELINE SUCCESS"
            echo "========================================"
            echo "Build: ${BUILD_NUMBER}"
            echo "Docker image: ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}"
            echo "Application: http://localhost:${CONTAINER_PORT}/automated-grocery-inventory-dashboard/items"
        }

        failure {
            echo "========================================"
            echo "FINAL PIPELINE FAILED"
            echo "========================================"
            echo "Check the failed stage logs."
            echo "Deployment stages after the failure were not executed."
        }
    }
}