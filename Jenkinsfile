// Practical 2: Build, push to Docker Hub, and deploy with Jenkins
// Prerequisites: Docker installed on the Jenkins host, and a Jenkins
// "Username with password" credential with ID 'docker-hub-credentials'.
// On Windows agents, replace `sh` with `bat` (and drop the `|| true` trick
// by using `docker rm -f my-web-app || exit 0`).
pipeline {
    agent any

    environment {
        DOCKER_IMAGE          = 'ishimwe250/my-web-app'   // <-- change to your Docker Hub username
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
        CONTAINER_NAME        = 'my-web-app'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install & Test') {
            steps {
                sh 'npm install'
                sh 'npm test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        dockerImage.push("${env.BUILD_NUMBER}")
                        dockerImage.push('latest')
                    }
                }
            }
        }

        stage('Deploy to Local Docker Host') {
            steps {
                sh '''
                    docker rm -f ${CONTAINER_NAME} || true
                    docker run -d --name ${CONTAINER_NAME} -p 8081:80 ${DOCKER_IMAGE}:latest
                '''
            }
        }

        // Use this stage instead of the local one for a remote Docker host.
        // Requires the SSH Agent plugin and a credential with ID 'remote-host-ssh-key'.
        /*
        stage('Deploy to Remote Docker Host') {
            steps {
                sshagent(['remote-host-ssh-key']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no user@remote-host "docker pull ${DOCKER_IMAGE}:latest"
                        ssh user@remote-host "docker rm -f ${CONTAINER_NAME} || true"
                        ssh user@remote-host "docker run -d --name ${CONTAINER_NAME} -p 8081:80 ${DOCKER_IMAGE}:latest"
                    '''
                }
            }
        }
        */

        stage('Verify Deployment') {
            steps {
                sh 'sleep 5 && curl -f http://localhost:8081'
            }
        }
    }

    post {
        failure {
            echo 'Deployment failed. Check the console output and container logs (docker logs my-web-app).'
        }
    }
}
