#!/bin/bash

HOME = `cd ~ | pwd`
APPNAME = "proxycorn"
mkdir -p ${HOME}/${APPNAME}
mkdir -p ${HOME}/${APPNAME}/filters
mkdir -p ${HOME}/${APPNAME}/plugins
mkdir -p ${HOME}/${APPNAME}/settings


java -Djava.library.path="/etc/${APPNAME}/plugins/*.jar" --module-path /etc/${APPNAME}/lib/jfx --add-modules=javafx.fxml,javafx.controls -jar /etc/${APPNAME}/lib/proxycorn-1.1-jar-with-dependencies.jar