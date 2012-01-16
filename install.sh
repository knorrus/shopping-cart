#!/bin/bash

dependenciesDir=deploy

databaseDir=database
defaultDatabaseHost="127.0.0.1"
defaultDatabaseUser="ShoppingCartOwner"
defaultDatabaseName="ShoppingCart"

if  [ -z "psql" ]
    then echo 'ERROR: You need to install psql'
    exit
fi

if  [ -z "mvn" ]
    then echo 'ERROR: You need to install maven2'
    exit
fi

cd $dependenciesDir || {
    echo "ERROR: Can't find deploy folder"
    exit
}

echo "Install dependencies to Maven repository..."
if [ -f kefir-bb-0.6.jar ]; then
    echo "Install kefir-BB library"
else
    echo "ERROR: File kefir-bb-0.6.jar not found"
    exit
fi
mvn install:install-file -Dfile=kefir-bb-0.6.jar -DgroupId=ru.perm -DartifactId=KefirBB -Dversion=0.6 -Dpackaging=jar || {
    echo "ERROR: Can't install dependency"
    exit
}

cd ..

cd $databaseDir || {
    echo "ERROR: Can't find database folder"
    exit
}

if [ -n "$1" ]
then
  defaultDatabaseUser=$1
fi

if [ -n "$2" ]
then
  defaultDatabaseName=$2
fi

if [ -n "$3" ]
then
  defaultDatabaseHost=$3
fi

if [ -f ShoppingCartCurrent.sql ];
then
    echo "Create database structure..."
else
    echo "ERROR: File ShoppingCartCurrent.sql not found"
    exit
fi

psql -U "$defaultDatabaseUser" -d "$defaultDatabaseName" -f "ShoppingCartCurrent.sql" -h "$defaultDatabaseHost" || {
    echo "ERROR: Can't create database structure"
    exit
}

exit 0