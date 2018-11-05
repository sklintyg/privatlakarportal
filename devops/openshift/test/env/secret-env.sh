#!/bin/bash

export DB_USERNAME=${DATABASE_USERNAME:-intyg}
export DB_PASSWORD=${DATABASE_PASSWORD:-intyg}
export REDIS_PASSWORD=${REDIS_PASSWORD:-redis}

export CATALINA_OPTS_APPEND="\
-Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
-Dconfig.folder=/opt/$APP_NAME/config \
-Dconfig.file=/opt/$APP_NAME/config/privatlakarportal.properties \
-Dmailresource.file=/opt/$APP_NAME/config/mailresource.properties \
-Dlogback.file=$LOGBACK_FILE \
-Dcertificate.folder=/opt/$APP_NAME/certifikat \
-Dcredentials.file=/opt/$APP_NAME/env/secret-env.properties \
-Dresources.folder=/tmp/resources \
-Dfile.encoding=UTF-8 \
-DbaseUrl=http://${APP_NAME}:8080"