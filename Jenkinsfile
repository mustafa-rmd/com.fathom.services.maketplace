pipeline {
    agent any
    stages {
        stage("release") {
            steps {
                script {
                    env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
                    env.SERVICE_NAME = env.GIT_REPO_NAME.replaceFirst(/(com\.fathom\.services\.)/, "")
                    env.BUILD_TOOL = 'maven'
                    env.ENVIRONMENT = env.BRANCH_NAME.replaceFirst('v2-', '')
                    env.Services = [env.GIT_URL, env.SERVICE_NAME, env.BUILD_TOOL].join(";")
                    echo "Building " + env.SERVICE_NAME + " service and branch " + env.BRANCH_NAME + " for environment " + env.ENVIRONMENT
                    build job: 'Control plane {K8s}', parameters: [string(name: 'Services', value: env.Services), string(name: 'ENVIRONMENT', value: env.ENVIRONMENT), string(name: 'Branch', value: env.BRANCH_NAME)]                    
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}