#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

dist=$bin/../target

HEAP_OPTS="-Xmx1g -Xms1g -XX:NewSize=256m"
#JAVA_DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=1044,server=y,suspend=y"

(cd $bin/..; java $JAVA_OPTS $JAVA_DEBUG $HEAP_OPTS -jar $dist/raytrace*.jar -i $@)
