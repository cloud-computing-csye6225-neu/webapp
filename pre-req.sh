#!/bin/bash
 
# sudo dnf update -y
# sudo dnf upgrade -y
 
# Install Java 17
echo "Installing Java"
sudo dnf install java-17-openjdk -y

# Update jdk version
echo "Updating JDK Version"
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk>> ~/.bashrc
export PATH=$JAVA_HOME/bin:$PATH>> ~/.bashrc

# Install Maven
echo "Installing Maven"
sudo dnf install maven -y
  
# Install MySQL
echo "Installing MySQL"
sudo dnf install mysql-server -y
 
# Start and enable MySQL service
sudo systemctl start mysqld
sudo systemctl enable mysqld
 
MYSQL_ROOT_PASSWORD="root"
 
# Set MySQL root password non-interactively
sudo mysqladmin -u root password "${MYSQL_ROOT_PASSWORD}"
 
# Secure MySQL installation (additional configurations)
sudo mysql -u root -p"${MYSQL_ROOT_PASSWORD}" <<EOF
DELETE FROM mysql.user WHERE User='';
DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost', '127.0.0.1', '::1');
DROP DATABASE IF EXISTS test;
DELETE FROM mysql.db WHERE Db='test' OR Db='test\_%';
FLUSH PRIVILEGES;
EOF
 
# sudo mysql -u root -p"${MYSQL_ROOT_PASSWORD}" <<EOF
# n
# n
# n
# n
# y;
# EOF
 
# sudo mysql_secure_installation <<EOF
 
# n
# n
# n
# n
# Y
# EOF
 
 
# Install Tomcat
echo "Start Tomcat Installation"
sudo dnf install -y tomcat
 
# Start and enable Tomcat service
sudo systemctl start tomcat
sudo systemctl enable tomcat
echo "Completed Tomcat Installation"
 
 
 
has context menu