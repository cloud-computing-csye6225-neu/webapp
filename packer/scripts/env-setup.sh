#!/bin/bash

# Create a new .env file
DB_URL=$DB_URL
DB_USERNAME=$DB_USERNAME
DB_PASSWORD=$DB_PASSWORD

# Append variables to .env file
echo "DB_HOST=$DB_URL" >> .env
echo "DB_USER=$DB_USERNAME" >> .env
echo "DB_PASSWORD=$DB_PASSWORD" >> .env

# Move .env file to /opt/
echo "Moving .env file to /opt/"
sudo mv .env /opt/

# setting enforce to 0
# sudo setenforce 0

# updating the permissions of the jar to user and password
sudo chown csye6225: /tmp/healthCheckAPI-0.0.1-SNAPSHOT.jar
sudo mv /tmp/healthCheckAPI-0.0.1-SNAPSHOT.jar /opt/
sudo chown csye6225: /tmp/csye6225.service 
sudo mv /tmp/csye6225.service /etc/systemd/system

# add the service to systemd
sudo systemctl daemon-reload
sudo systemctl start csye6225
sudo systemctl enable csye6225