#!/bin/bash
mvn clean package -f ./backend/XMeme/pom.xml -DskipTests
nohup java -jar ./backend/XMeme/target/XMeme.jar > logs.log 2>&1 &