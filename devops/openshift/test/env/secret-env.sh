#!/bin/bash
# Assign backing service addresses from the outer environment

export DB_USERNAME=${DATABASE_USERNAME:-intyg}
export DB_PASSWORD=${DATABASE_PASSWORD:-intyg}
export DB_NAME=${DATABASE_NAME:-privatlakarportaltest}
export DB_SERVER=mysql
export DB_PORT=$MYSQL_SERVICE_PORT

export REDIS_PASSWORD=${REDIS_PASSWORD:-redis}
export REDIS_PORT=$REDIS_SERVICE_PORT
export REDIS_HOST=$REDIS_SERVICE_HOST

# dev profile is default for pipeline
SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-test,pp-security-test,hsa-stub,wc-stub,wc-pu-stub,mail-stub,testability-api,pp-init-data,caching-enabled}

export CATALINA_OPTS_APPEND="\
-Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
-Dconfig.folder=/opt/$APP_NAME/config \
-Dconfig.file=/opt/$APP_NAME/config/privatlakarportal.properties \
-Dlogback.file=/opt/$APP_NAME/config/privatlakarportal-logback.xml \
-Dmailresource.file=/opt/$APP_NAME/config/mailresource.properties
-Dcertificate.folder=/opt/$APP_NAME/certifikat \
-Dcredentials.file=/opt/$APP_NAME/env/secret-env.properties \
-Dresources.folder=/tmp/resources \
-Dfile.encoding=UTF-8 \
-DbaseUrl=http://${APP_NAME}:8080 \
-Dwebcert.baseUrl=http://webcert-${APP_NAME}:8080"
