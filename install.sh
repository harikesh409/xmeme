#!/bin/bash

# Updating the system
sudo apt-get update -y && sudo apt-get dist-upgrade -y

# Installing MySQL
sudo apt-get install -q -y -o Dpkg::Options::="--force-confdef" mysql-server;

# Setting up the database
sudo mysql -e "SET PASSWORD FOR root@localhost = PASSWORD('root');FLUSH PRIVILEGES;"
sudo mysql -e "DELETE FROM mysql.user WHERE User='';"
sudo mysql -e "DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost', '127.0.0.1', '::1');"
sudo mysql -u root -proot -e "CREATE USER 'harikesh'@'localhost' IDENTIFIED BY 'harikesh';GRANT ALL PRIVILEGES ON *.* TO 'harikesh'@'localhost';FLUSH PRIVILEGES;"
sudo mysql -uroot -proot -e 'create database IF NOT EXISTS xmeme;'
service mysql restart

# Installing Java
sudo apt-get install -y openjdk-8-jdk

# Installing maven
sudo apt-get install -y maven