// Practical 1: Jenkins pipeline for a Node.js web app (Windows agent)
// On Linux/macOS agents, replace every `bat` with `sh`.
pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                echo 'Cloning source code from GitHub...'
                checkout scm
            }
        }

        stage('Install Dependencies') {
            steps {
                echo 'Installing npm dependencies...'
                bat 'npm install'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                bat 'npm run build --if-present'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'npm test'
            }
        }

        stage('Deploy to Staging') {
            steps {
                echo 'Tests passed. Deploying to staging...'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
}
