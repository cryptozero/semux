#!/bin/sh

# change work directory
cd "$(dirname "$0")"

# check java version
version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f2)
if [ $version -lt "8" ]; then
    echo "Error: Java 8 or above is required"
    exit 1
fi

# start kernel
java -cp "./lib/*" org.semux.CLI $@
