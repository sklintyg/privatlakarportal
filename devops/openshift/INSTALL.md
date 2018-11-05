
# OPENSHIFT INSTALLATION GUIDE

Installation of web application privatlakarportal (PP) on openshift.

## 1 Pre-Installation Requirements

The following prerequisites and requirements must be satisfied in order for the privatlakarportal (PP) to install successfully.


### 1.1 Backing Service Dependencies

The application has the following external services: 

Provided by operations (execution environment):

* MySQL (provided)
* Redis Sentinel (provided)
* Redis Server (provided)
* WebCert Web Service
* SMTP Mail Service

Provided elsewhere:

* Inera Service Platform (NTjP)
* CGI SAML IdP
* HSA Web Service

For all backing services their actual addresses and user accounts have to be known prior to start the installation procedure.  

### 1.3 Integration / Firewall

PP communicates in/out with the Inera Service Platform and thus needs firewall rules for that access.

### 1.4 Certificates

PP requires certificates, keystores and truststores for Inera Service Platform, CGI IdP, Webcert and HSA Web Service. The operations provider is responsible for installing these certificates in the appropriate OpenShift "secret", see detailed instructions in the OpenShift section.

### 1.5 Message Queues

None.

### 1.5 Database

A database for the application must have been created.  It's recommended to use character set `utf8mb4` and case-sensitive collation. 

### 1.6 Access to Software Artifacts

Software artifacts are located at, and downloaded from:

* From Installing Client - https://build-inera.nordicmedtest.se/nexus/repository/releases/se/inera/intyg/privatlakarportal/
* From OpenShift Cluster - docker.drift.inera.se/privatlakarportal/

### 1.7 Access to OpenShift Cluster

The OpenShift user account must have the right permissions to process, create, delete and replace objects, and most certainly a VPN account and connection is required in order to access the OpenShift Cluster.

### 1.8 Client Software Tools

The installation client must have **git** and **oc** (OpenShift Client) installed and if a database schema migration is required then **java** (Java 8) and **tar** is required in order to execute the migration tool (liquibase runner).

Must have:

* git
* oc
* VPN Client (such as Cisco Any Connect) 

To run database migration tool:

* java
* tar

# 2. Installation Procedure

### 2.1 Installation Checklist

1. All Pre-Installation Requirements are fulfilled, se above
2. Check if a database migration is required
3. Ensure that the env secret and secret-envvar are up to date
4. Ensure that the configmap and configmap-envvar are up to date
5. Check that deployment works as expected 
6. Fine-tune memory settings for container and java process
7. Setup policies for number of replicas, auto-scaling and rolling upgrade strategy


### 2.2 Migrate Database Schema

Prior to any release that includes changes to the database schema, the operations provider must execute schema updates using the Liquibase runner tool provided in this section. 

_Please note: a complete database backup is recommended prior to run the database migration tool_

Replace `<version>` below with the actual application version.

Fetch the actual version of the tool, the example below runs `wget` to retrieve the package (tarball).

    > wget https://build-inera.nordicmedtest.se/nexus/repository/releases/se/inera/intyg/privatlakarportal/privatlakarportal-liquibase-runner/<version>/privatlakarportal-liquibase-runner-<version>.tar


Download the tool to a computer with Java installed and access to the database in question.

    > tar xvf privatlakarportal-liquibase-runner-<version>.tar
    > cd privatlakarportal-liquibase-runner-<version>
    > bin/privatlakarportal-liquibase-runner --url=jdbc:mysql://<database-host>/<database-name> --username=<database_username> --password=<database_password> update


### 2.3 Get Source for Configuration


##### 2.3.1 Clone the repository

Clone repository and switch to the release branch specified in the release notes. Substitute `<name>` below with the actual release name, such as `2018-4`.
    
    > git clone https://github.com/sklintyg/privatlakarportal.git
    > git checkout release/<name>
    > cd devops/openshift
    
Note that we strongly recommend using a git account that has read-only (e.g. public) access to the repo.
    
##### 2.3.2 Log-in into the cluster

Use oc to login and select the actual project, e.g:

    > oc login https://path.to.cluster
    username: ******
    password: ******
    > oc project <name>

### 2.3.3 Ensure that the latest deployment template is installed

Down load and install the latest _[deploytemplate-webapp.yaml](https://github.com/sklintyg/tools/blob/develop/devops/openshift/deploytemplate-webapp.yaml)_

Syntax to create or replace the template: 

	> oc [ create | replace ] -f deploytemplate-webapp.yaml

### 2.4 Update configuration placeholders

For security reasons, no secret properties or configuration may be checked into git. Thus, a number of placeholders needs to be replaced prior to creating or updating secrets and/or config maps.

Open _&lt;env>/secret-vars.yaml_ and replace `<value>` with expected values:

	  DB_USERNAME: "<value>"
	  DB_PASSWORD: "<value>"
	  REDIS_PASSWORD: "<value>"
	  MAIL_USERNAME: "<value>"
	  MAIL_PASSWORD: "<value>"
	  CGI_ELEG_SAML_KEYSTORE_PASSWORD: "<value>"
	  HSA_WS_CERTIFICATE_PASSWORD: "<value>"
	  HSA_WS_KEY_MANAGER_PASSWORD: "<value>"
	  HSA_WS_TRUSTSTORE_PASSWORD: "<value>"
	  NTJP_WS_CERTIFICATE_PASSWORD: "<value>"
	  NTJP_WS_KEY_MANAGER_PASSWORD: "<value>"
	  NTJP_WS_TRUSTSTORE_PASSWORD: "<value>"
	  WEBCERT_WS_TRUSTSTORE_PASSWORD: "<value>

Open _&lt;env>/configmap-vars.yaml_ and replace `<value>` with expected values. You may also update the names of keystore/truststore files as well as their type (JKS or PKCS12). Also see working example from [privatlakarportal-test-configmap-envvar](https://raw.githubusercontent.com/sklintyg/privatlakarportal/develop/devops/openshift/test/configmap-vars.yaml). 


	SPRING_PROFILES_ACTIVE: "<value>"
	HSA_WS_SERVICES_URL: "<value>"
	PRIVATLAKARPORTAL_HOST_URL: "<value>"
	WEBCERT_HOST_URL: "<value>"
	WEBCERT_INTERNAL_HOST_URL: "<value>"
	LOGBACK_FILE: "<value>"
	REDIS_HOST: "<value>"
	REDIS_PORT: "<value>"
	REDIS_SENTINEL_MASTER_NAME: "<value>"
	MAIL_HOST: "<value>"
	DB_PORT: "<value>"
	DB_SERVER: "<value>"
	DB_NAME: "${database.name}"
	PUTJANST_LOGICALADDRESS: "<value>"
	HSA_WS_SERVICE_LOGICALADDRESS: "<value>"
	NTJP_BASE_URL: "<value>"
	NTJP_WS_CERTIFICATE_FILE: "<value>"
	NTJP_WS_TRUSTSTORE_FILE: "<value>"
	NTJP_WS_CERTIFICATE_TYPE: "[ JKS | PKCS12 ]"
	NTJP_WS_TRUSTSTORE_TYPE: "[ JKS | PKCS12 ]"
	WEBCERT_WS_TRUSTSTORE_FILE: "<value>"
	WEBCERT_WS_TRUSTSTORE_TYPE: "[ JKS | PKCS12 ]"
	CGI_ELEG_SAML_KEYSTORE_FILE: "file://<value>"
	CGI_ELEG_SAML_KEYSTORE_ALIAS: "<value>"
	HSA_WS_CERTIFICATE_FILE: "<value>"
	HSA_WS_CERTIFICATE_TYPE: "[ JKS | PKCS12 ]"
	HSA_WS_TRUSTSTORE_FILE: "<value>"
	HSA_WS_TRUSTSTORE_TYPE: "[ JKS | PKCS12 ]"
   
Note 1: The `DATABASE_NAME` variable is assumed to be defined within the application deployment config.

Note 2: Other properties might be used to define a `<value>`. As an example is the path to certificates indicated by the `certificate.folder` Java System Property, thus the truststore file can be defined as:
 
	NTJP_WS_TRUSTSTORE_FILE: "${certificate.folder}/truststore.jks"
    
            
##### 2.4.1 Redis Sentinel Configuration

Redis sentinel needs at least three addresses to work correctly. These are specified in the _redis.host_ and _redis.port_ properties respectively (in Java property file format):

    redis.host=host1;host2;host3
    redis.port=26379;26379;26379
    redis.sentinel.master.name=master
    
### 2.5 Prepare Certificates

The `<env>` placeholder shall be substituted with the actual name of the environment such as `stage` or `prod`.

Staging and Prod certificates are **never** committed to git. However, you may temporarily copy them to _&lt;env>/certifikat_ in order to install/update them. Typically, certificates have probably been installed separately. The important thing is that the deployment template **requires** a secret named: `privatlakarportal[-<env>]-certifikat` to be available in the OpenShift project. It will be mounted to _/opt/privatlakarportal[-<env>]/certifikat_ in the container file system.


### 2.6 Creating Config and Secrets
If you've finished updating the files above, it's now time to use **oc** to install them into OpenShift.
All commands must be executed from the same folder as this markdown file, i.e. _/privatlakarportal/devops/openshift_ 

Note: To delete an existing ConfigMap or Secret use the following syntax:

	> oc delete [ configmap | secret ] <name>

##### 2.6.1 Create environment variables for Secret and ConfigMap
From YAML-files, their names are hard-coded into the respective file

    > oc create -f <env>/configmap-vars.yaml
    > oc create -f <env>/secret-vars.yaml
    
##### 2.6.2 Create Secret and ConfigMap
Creates config map and secret from the contents of the _&lt;env>/env_ and _&lt;env>/config_ folders:

    > oc create configmap privatlakarportal[-<env>]-config --from-file=<env>/config/
    > oc create secret generic privatlakarportal[-<env>]-env --from-file=<env>/env/ --type=Opaque
    
##### 2.6.3 Create Secret with Certificates
If this hasn't been done previously, you may **temporarily** copy keystores into the _&lt;env>/certifikat_ folder and then install them into OpenShift using this command:

    > oc create secret generic privatlakarportal[-<env>]-certifikat --from-file=<env>/certifikat/ --type=Opaque

### 2.7 Deploy
We're all set for deploying the application. As stated in the pre-reqs, the "deploytemplate-webapp" must be installed in the OpenShift project.

**NOTE 1** You need to reference the correct docker image from the Nexus!! You must replace \<replaceme\> with a correct path to the image to deploy!!

**NOTE 2** Please specify the `DATABASE_NAME` actual MySQL database. Default is **privatlakarportal**.

Run the following command to create a deployment:

    > oc process deploytemplate-webapp \
        -p APP_NAME=privatlakarportal[-<env>] \
        -p IMAGE=docker.drift.inera.se/intyg/privatlakarportal:<version> \
        -p STAGE=<env> 
        -p DATABASE_NAME=<value> \
        -o yaml | oc apply -f -
        
        
Alternatively, it's possible to use the deploytemplate-webapp file locally:

    > oc process -f deploytemplate-webapp.yaml -p APP_NAME= privatlakarportal[-<env>] ...

### 2.8 Verify
The pod(s) running privatlakarportal should become available within a few minutes use **oc** or Web Console to checkout the logs for progress:

	> oc logs dc/privatlakarportal[-<env>]

### 2.9 Routes
To publish PP a corresponding OCP route has to be created. The internal service address is _http://privatlakarportal-&lt;env>:8080_. The route should only accept `HTTPS` and is responsible of TLS termination.
