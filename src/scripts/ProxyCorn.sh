#!/bin/bash
java -Djava.library.path="/etc/proxycorn/lib/plugins/*.jar" --module-path /etc/proxycorn/lib/jfx --add-modules=javafx.fxml,javafx.controls -jar /etc/proxycorn/lib/proxycorn-1.1-jar-with-dependencies.jar