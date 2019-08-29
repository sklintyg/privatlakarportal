#!/bin/bash

export CATALINA_OPTS_APPEND="\
-Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
-Dconfig.folder=/opt/$APP_NAME/config \
-Dmailresource.file=/opt/$APP_NAME/config/mailresource.properties \
-Dlogback.file=$LOGBACK_FILE \
-Dcertificate.folder=/opt/$APP_NAME/certifikat \
-Dcredentials.file=/opt/$APP_NAME/env/secret-env.properties \
-Dresources.folder=classpath: \
-Dfile.encoding=UTF-8 \
-DbaseUrl=http://${APP_NAME}:8080"