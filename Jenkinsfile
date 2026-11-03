pipeline {
    agent any

    options { disableConcurrentBuilds() }

    environment {
        IMAGE_NAME = "petstore-api-tests"
        ALLURE_RESULTS = "allure-results"
        ALLURE_REPORT = "allure-report"
    }

    stages {

        stage('Checkout') {
            steps {
                script {
                    env.CONTAINER_NAME = "petstore-" + env.BUILD_TAG.replaceAll("[^a-zA-Z0-9_.-]", "-")
                }
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Run Tests') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh 'docker run --name "$CONTAINER_NAME" "$IMAGE_NAME"'
                }
            }
        }

        stage('Extract Allure Results') {
            steps {
                sh """
                rm -rf ${ALLURE_RESULTS}
                mkdir -p ${ALLURE_RESULTS}
                docker cp ${CONTAINER_NAME}:/app/target/allure-results/. ./${ALLURE_RESULTS}
                """
            }
        }

        stage('Generate Allure Report') {
            steps {
                allure(
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']]
                )
            }
        }
    }

    post {
        always {

            // Clean container
            sh """
            docker rm -f ${CONTAINER_NAME} || true
            """

            // Email report
            emailext(
                subject: "API Test Report - Build #${BUILD_NUMBER}",
                body: """
                <p>API automation execution is completed.</p>
                <p><b>Build:</b> ${BUILD_NUMBER}</p>
                <p><b>Status:</b> ${currentBuild.currentResult}</p>
                <p>
                    <a href="${BUILD_URL}allure/">Click here to view Allure Report</a>
                </p>
                """,
                mimeType: 'text/html',
                to: 'wagdy.abdelrahman@gmail.com'
            )
        }
    }
}

