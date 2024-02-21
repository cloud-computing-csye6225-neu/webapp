#!/bin/bash

# Create a new .env file
touch .env
echo $1
echo $2
echo $3
DB_URL=$1
DB_USERNAME=$2
DB_PASSWORD=$3

# Append variables to .env file
echo "DB_HOST=$DB_URL" >> .env
echo "DB_USER=$DB_USERNAME" >> .env
echo "DB_PASSWORD=$DB_PASSWORD" >> .env

# Move .env file to /tmp/
echo "Moving .env file to /tmp/"
mv .env /tmp/

