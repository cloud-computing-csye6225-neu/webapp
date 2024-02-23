#!/bin/bash
 
# sudo dnf update -y
# sudo dnf upgrade -y
 
# Install Java 17
echo "Installing Java"
sudo yum -y install wget vim
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
sudo yum -y install ./jdk-17_linux-x64_bin.rpm

# Update jdk version
echo "Updating JDK Version"
export JAVA_HOME=/usr/lib/jvm/jdk-17-oracle-x64/ >> /etc/environment
source /etc/environment

# Install Maven
echo "Installing Maven"
sudo dnf install maven -y
  
# Install MySQL
echo "Installing MySQL"
sudo dnf install mysql-server -y
 
# Start and enable MySQL service
sudo systemctl start mysqld
sudo systemctl enable mysqld
 
MYSQL_ROOT_PASSWORD=root
 
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
 