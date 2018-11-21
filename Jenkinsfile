#!groovy

def buildVersion = "1.7.0.${BUILD_NUMBER}"
def infraVersion = "3.6.0.+"
def commonVersion = "3.6.0.+"

stage('checkout') {
    node {
	git url: "https://github.com/sklintyg/privatlakarportal.git", branch: GIT_BRANCH
        util.run { checkout scm }
    }
}

stage('build') {
    node {
        try {
            shgradle "--refresh-dependencies clean build testReport sonarqube -PcodeQuality -DgruntColors=false -DbuildVersion=${buildVersion} -DinfraVersion=${infraVersion} -Dprivatlakarportal.useMinifiedJavaScript"
        } finally {
            publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/allTests', \
                reportFiles: 'index.html', reportName: 'JUnit results'
        }
    }
}

stage('deploy') {
    node {
        util.run {
            ansiblePlaybook extraVars: [version: buildVersion, ansible_ssh_port: "22", deploy_from_repo: "false", config_version: "PP-1.6"], \
                installation: 'ansible-yum', inventory: 'ansible/inventory/privatlakarportal/test', playbook: 'ansible/deploy.yml'
            util.waitForServer('http://privatlakarportal.inera.nordicmedtest.se/version.jsp', false)
        }
    }
}

stage('restAssured') {
    node {
        try {
            shgradle "restAssuredTest -DbaseUrl=http://privatlakarportal.inera.nordicmedtest.se/ -DbuildVersion=${buildVersion} -DinfraVersion=${infraVersion}"
        } finally {
            publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'web/build/reports/tests/restAssuredTest', \
               reportFiles: 'index.html', reportName: 'RestAssured results'
        }
    }
}

stage('cypress') {
    node {
        try {
            shgradle "cypressTest -DbaseUrl=https://privatlakarportal.inera.nordicmedtest.se"
        } finally {
            publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'test/build/test-results', \
                reportFiles: 'mochawesome.html', reportName: 'Cypress results'
        }
    }
}

//stage('fitnesse') {
//    node {
//        try {
//            wrap([$class: 'Xvfb']) {
//                shgradle "fitnesseTest -Penv=build-server -PfileOutput -PoutputFormat=html \
//                          -DcommonVersion=${commonVersion} -DinfraVersion=${infraVersion}"
//            }
//        } finally {
//            publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'specifications/', \
//                reportFiles: 'fitnesse-results.html', reportName: 'Fitnesse results'
//        }
//    }
//}

stage('tag and upload') {
    node {
	shgradle "uploadArchives tagRelease -DbuildVersion=${buildVersion} -DinfraVersion=${infraVersion} -Dprivatlakarportal.useMinifiedJavaScript"
    }
}
