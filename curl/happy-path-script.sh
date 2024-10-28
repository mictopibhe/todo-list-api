#!/bin/bash
#OUTPUT_PATH
OUTPUT_PATH="response.json"
# BASE_URL API
BASE_URL="https://localhost:8080/api/v1"
#ADMIN_DATA
ADMIN_SIGN_IN="admin@gmail.com:12345678"
FIRST_USER_SIGN_IN=""
SECOND_USER_SIGN_IN=""
#ENDPOINTS
SIGN_UP="/auth/signup"


#Create two users
#First user
FIRST_USER='{
      "firstName": "Dmytro",
      "lastName": "Krasko",
      "email": "krasko@gmail.com",
      "password": "12345678",
      "confirmPassword":"12345678"
}'
#Second user
SECOND_USER='{
              "firstName": "Nadia",
              "lastName": "Skobel",
              "email": "skobel@gmail.com",
              "password": "12345678",
              "confirmPassword": "12345678"
            }'

curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/signup' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d "$FIRST_USER"\
  -o "logfile_$(date +%Y-%m-%d).log"
  echo "" >> "logfile_$(date +%Y-%m-%d).log"

curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/signup' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d "$SECOND_USER"\
  >> "logfile_$(date +%Y-%m-%d).log"
  echo "" >> "logfile_$(date +%Y-%m-%d).log"

#Creation of two tasks for each user
FIRST_TASK='{
  "title": "first task",
  "description": "first task description"
}'

SECOND_TASK='{
  "title": "second task",
  "description": "second task description"
}'

#Creation of tasks for first user
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/signup' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d "$FIRST_USER"\
  -o "logfile_$(date +%Y-%m-%d).log"
  echo "" >> "logfile_$(date +%Y-%m-%d).log"