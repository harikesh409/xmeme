#!/bin/bash
sudo apt-get update -y && sudo apt-get dist-upgrade -y
#echo mysql-server-5.7 mysql-server/root_password password root | debconf-set-selections
#echo mysql-server-5.7 mysql-server/root_password_again password root | debconf-set-selections
sudo apt-get install -q -y -o Dpkg::Options::="--force-confdef" mysql-server;
#mysql -uroot -proot -e 'USE mysql; UPDATE `user` SET `Host`="%" WHERE `User`="root" AND `Host`="localhost"; DELETE FROM `user` WHERE `Host` != "%" AND `User`="root"; FLUSH PRIVILEGES;'
sudo mysql -e "SET PASSWORD FOR root@localhost = PASSWORD('root');FLUSH PRIVILEGES;"
sudo mysql -e "DELETE FROM mysql.user WHERE User='';"
sudo mysql -e "DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost', '127.0.0.1', '::1');"
#sudo mysql -e "DROP DATABASE test;DELETE FROM mysql.db WHERE Db='test' OR Db='test_%';"
sudo mysql -u root -proot -e "CREATE USER 'harikesh'@'localhost' IDENTIFIED BY 'harikesh';GRANT ALL PRIVILEGES ON *.* TO 'harikesh'@'localhost';FLUSH PRIVILEGES;"
sudo mysql -uroot -proot -e 'create database IF NOT EXISTS xmeme;';
service mysql restart
#yes '' | sudo add-apt-repository ppa:openjdk-r/ppa
#sudo apt-get update -y && sudo apt-get dist-upgrade -y
#sudo apt-get install -y openjdk-8-jdk
# Installing maven
sudo apt-get install -y maven