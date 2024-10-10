#!/bin/bash
#OUTPUT_PATH
OUTPUT_PATH="response.log"
# BASE_URL API
BASE_URL="https://localhost:8080/api/v1"
#ADMIN_DATA
ADMIN_SIGN_IN="admin@gmail.com:12345678"
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
      "lastName": "Scobel",
      "email": "scobel@gmail.com",
      "password": "12345678",
      "confirmPassword":"12345678"
}'

curl --request POST -sL \
     --url "${BASE_URL}${SIGN_UP}"\
     --header "Content-Type: application/json"\
     --header "Accept-Language: uk"\
     --data "$FIRST_USER"\
     --output "$OUTPUT_PATH"

curl --request POST -sL \
          --url "${BASE_URL}${SIGN_UP}"\
          --header "Content-Type: application/json"\
          --header "Accept-Language: pl"\
          --data "$SECOND_USER"\
          --output "$OUTPUT_PATH"