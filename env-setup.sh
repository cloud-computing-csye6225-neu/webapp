#!/bin/bash

# Create a new .env file
# touch .env
# echo "$1"
# echo "$2"
# echo "$3"
# DB_URL="$1"
# DB_USERNAME="$2"
# DB_PASSWORD="$3"

# # Append variables to .env file
# echo "DB_HOST=$DB_URL" >> .env
# echo "DB_USER=$DB_USERNAME" >> .env
# echo "DB_PASSWORD=$DB_PASSWORD" >> .env

# # Move .env file to /tmp/
# echo "Moving .env file to /tmp/"
# mv .env /tmp/

# updating the permissions of the jar to user and password
sudo chown csye6225: /opt/healthCheckAPI-0.0.1-SNAPSHOT.jar
sudo chown csye6225: /opt/csye6225.service
sudo mv /opt/csye6225.service /etc/systemd/system

# add the service to systemd
sudo systemctl daemon-reload
sudo systemctl start csye6225
sudo systemctl enable csye6225