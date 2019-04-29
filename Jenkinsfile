#!groovy

def buildVersion = "1.11.0.${BUILD_NUMBER}"
def infraVersion = "3.10.0.+"
def refDataVersion = "1.0-SNAPSHOT"

stage('checkout') {
    node {
	git url: "https://github.com/sklintyg/privatlakarportal.git", branch: GIT_BRANCH
        util.run { checkout scm }
    }
}

stage('build') {
    node {
        try {
            shgradle "--refresh-dependencies clean build testReport sonarqube -PcodeQuality -DgruntColors=false -DbuildVersion=${buildVersion} -DinfraVersion=${infraVersion} -DuseMinifiedJavaScript"
        } finally {
            publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/allTests', \
                reportFiles: 'index.html', reportName: 'JUnit results'
        }
    }
}

stage('tag') {
    node {
	    shgradle tagRelease -DbuildVersion=${buildVersion} -DinfraVersion=${infraVersion} -DuseMinifiedJavaScript"
    }
}

stage('propagate') {
    node {
        gitRef = "v${buildVersion}"
        releaseFlag = "${GIT_BRANCH.startsWith("release")}"
        build job: "privatlakarportal-dintyg-build", wait: false, parameters: [
                [$class: 'StringParameterValue', name: 'PRIVATLAKARPORTAL_BUILD_VERSION', value: buildVersion],
                [$class: 'StringParameterValue', name: 'INFRA_VERSION', value: infraVersion],
                [$class: 'StringParameterValue', name: 'REF_DATA_VERSION', value: refDataVersion],
                [$class: 'StringParameterValue', name: 'GIT_REF', value: gitRef],
                [$class: 'StringParameterValue', name: 'RELEASE_FLAG', value: releaseFlag]
        ]
    }
}

stage('notify') {
    node {
        util.notifySuccess()
    }
}

