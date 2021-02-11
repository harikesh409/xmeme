#!/bin/bash
sudo apt-get -y update

# Installing Java
sudo apt-get install -y openjdk-8-jdk

# Installing MySQL
# Install MySQL Server in a Non-Interactive mode. Default root password will be "root"
export DEBIAN_FRONTEND="noninteractive"
sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password root"
sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password root"
sudo apt install -y mysql-server

# Run the MySQL Secure Installation wizard
sudo mysql_secure_installation
# Entering MySQL Prompt
sudo mysql -uroot -p -e 'create database IF NOT EXISTS xmeme;';
# Create user
#ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
#FLUSH PRIVILEGES;
# Grant permissions
# Create Database
#create database xmeme;
#exit;
# Installing maven
sudo apt-get install -y maven