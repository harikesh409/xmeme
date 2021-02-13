#!/bin/bash
# git clone the repo
# cd to the cloned repo directory
docker-compose up -d
# 3. Add a sleep timer to sleep.sh depending upon how long you want to sleep so that the server is ready.
chmod +x sleep.sh
./sleep.sh
# Execute the GET /memes endpoint using curl to ensure your DB is in a clean slate
# Should return an empty array.
curl --location --request GET 'http://localhost:8081/memes'
# Execute the POST /memes endpoint using curl
curl --location --request POST 'http://localhost:8081/memes' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "xyz",
"url": "https://ichef.bbci.co.uk/images/ic/704xn/p072ms6r.jpg",
"caption": "This is a meme"
}'
# Execute the GET /memes endpoint using curl
curl --location --request GET 'http://localhost:8081/memes'
# If you have swagger enabled, make sure it is exposed at localhost:8080
curl --location --request GET 'http://localhost:8081/swagger-ui/'