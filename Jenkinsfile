pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'khandelwaltarun007/user-service-elk-stack'
        DOCKER_CREDENTIALS_ID = '<DOCKER_CREDENTIALS>'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/khandelwaltarun007/elk-spring-boot.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_IMAGE .'
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]) {
                    sh """
                        echo $DOCKERHUB_PASS | docker login -u $DOCKERHUB_USER --password-stdin
                        docker push $DOCKER_IMAGE
                        docker logout
                    """
                }
            }
        }

        stage('Deploy Container Locally') {
            steps {
                sh """
                    docker rm -f yourapp-container || true
                    docker run -d --name yourapp-container -p 8080:8080 $DOCKER_IMAGE
                """
            }
        }
    }
}
