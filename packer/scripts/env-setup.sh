#!/bin/bash

set -e

# setting enforce to 0
echo "SELINUX=permissive" | sudo tee /etc/selinux/config

# Install google opsagent
echo "Instaling google ops-agent"
curl -sSO https://dl.google.com/cloudagents/add-google-cloud-ops-agent-repo.sh
sudo bash add-google-cloud-ops-agent-repo.sh --also-install

# update the configuration file
echo "updating the agent configuration"
sudo mv -f /tmp/config.yaml /etc/google-cloud-ops-agent/
sudo chown -R csye6225:csye6225 /etc/google-cloud-ops-agent/
#creating logs folder
sudo mkdir -p /var/log/webapp/

#update the permissions of the logs folder
sudo chown csye6225:csye6225 /var/log/webapp/
sudo chmod -R 755 /var/log/webapp

# updating the permissions of the jar to user and password
sudo chown csye6225: /tmp/healthCheckAPI-0.0.1-SNAPSHOT.jar
sudo mv /tmp/healthCheckAPI-0.0.1-SNAPSHOT.jar /home/csye6225/
sudo chown csye6225: /tmp/csye6225.service 
sudo mv /tmp/csye6225.service /etc/systemd/system

sudo chown csye6225: /tmp/csye6225-path.path
sudo mv /tmp/csye6225-path.path /etc/systemd/system

# add the service to systemd
sudo systemctl daemon-reload 
sudo systemctl enable csye6225.service
sudo systemctl restart google-cloud-ops-agent
sudo systemctl enable csye6225-path.path

########################################################################
#              UPDATED THE DATABASE TO NEW GCP INSTANCE                #
########################################################################

# Create a new .env file
# DB_URL=$DB_URL
# DB_USERNAME=$DB_USERNAME
# DB_PASSWORD=$DB_PASSWORD

# Append variables to .env file
# echo "DB_URL='$DB_URL'" >> .env
# echo "DB_USERNAME=$DB_USERNAME" >> .env
# echo "DB_PASSWORD=$DB_PASSWORD" >> .env

# Move .env file to /opt/
# echo "Moving .env file to /opt/"
# sudo mv .env /home/csye6225/
# sudo chown csye6225: /home/csye6225/.env
