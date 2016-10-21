#!groovy

def buildVersion = "1.2.${BUILD_NUMBER}"

stage('checkout') {
    node {
        util.run { checkout scm }
    }
}

stage('build') {
    node {
        try {
            shgradle "--refresh-dependencies clean build testReport sonarqube -PcodeQuality -DgruntColors=false \
                  -PprojectId=privatlakarportal -PprojectName=Privatlakarportal -DbuildVersion=${buildVersion}"
        } finally {
            publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/allTests', \
                reportFiles: 'index.html', reportName: 'JUnit results'
        }
    }
}

stage('deploy') {
    node {
        util.run {
            ansiblePlaybook extraVars: [version: buildVersion, ansible_ssh_port: "22", deploy_from_repo: "false"], \
                installation: 'ansible-yum', inventory: 'ansible/hosts_test', playbook: 'ansible/deploy.yml'
        }
    }
}

stage('fitnesse') {
    node {
	timeout(240) {
	    waitUntil {
		def r = sh script: 'wget -q https://privatlakarportal.inera.nordicmedtest.se/version.jsp -O /dev/null', returnStatus: true
		return (r == 0);
	    }
	}
        try {
            wrap([$class: 'Xvfb']) {
                shgradle "fitnesseTest -Penv=build-server -PfileOutput -PoutputFormat=html"
            }
        } finally {
            publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'specifications/', \
                reportFiles: 'fitnesse-results.html', reportName: 'Fitnesse results'
        }
    }
}

stage('tag and upload') {
    node {
	shgradle "uploadArchives tagRelease -DbuildVersion=${buildVersion}"
    }
}
