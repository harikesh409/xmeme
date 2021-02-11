# XMeme
[![Netlify Status](https://api.netlify.com/api/v1/badges/b1b59b4a-94c3-4374-a5f6-75dd2487eeee/deploy-status)](https://app.netlify.com/sites/harikesh-xmeme/deploys)

Meme Stream Page where users can post memes by providing their name, a caption for the meme and the URL for the meme image as input.
## Technology Stack
### Front End
- HTML
- CSS
- Vannila Javascript
- Bootstrap 5
- Material UI
### Back End
- Java 8
- Spring Boot v2.4.2
- Hibernate
- Swagger
- Lombok
- Mockito
- JUnit 5
## Prerequisites
- [Java](https://www.java.com/en/download/ "Java 8+")
- [Maven](http://https://maven.apache.org/download.cgi "Maven")
- [VS Code](https://code.visualstudio.com/download "VS Code") or any Preffered IDE
- [Eclipse](https://www.eclipse.org/downloads/ "Eclipse") or any other Java supported IDE
## Running Backend
1.  First clone the repository using the following command:
```git clone git@gitlab.crio.do:COHORT_ME_BUILDOUT_XMEME_ENROLL_1612436694845/p-harikesh409-me_buildout_xmeme.git```
2. Change the current directory to the backend directory of the clone repo.
```bash
cd p-harikesh409-me_buildout_xmeme/backend/XMeme/
```
3. Run the backend server using maven.
```mvnw spring-boot:run```
4. By default it will run on port 8081.

### Endpoints
1. Get all Memes - GET Method
The pagenumber and pagesize parameters are optional.
[http://localhost:8081/memes?pageNumber=0&pageSize=100](http://localhost:8081/memes?pageNumber=0&pageSize=100 "http://localhost:8081/memes?pageNumber=0&pageSize=100")
CURL example:
```bash
curl --location --request GET 'http://localhost:8081/memes'
```
2. Get Meme by ID - GET Method
Pass the meme id in the url.
[http://localhost:8081/memes/id](http://localhost:8081/memes/id "http://localhost:8081/memes/id")
CURL example:
```bash
curl --location --request GET 'http://localhost:8081/memes/1'
```
3. Add Meme - POST Method
Pass name, url, and caption on request body as json.
[http://localhost:8081/memes](http://localhost:8081/memes "http://localhost:8081/memes")
CURL example:
```bash
curl --location --request POST 'http://localhost:8081/memes' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "xyz",
"url": "https://ichef.bbci.co.uk/images/ic/704xn/p072ms6r.jpg",
"caption": "This is a meme"
}'```

4. Update Meme - PATCH Method
Pass meme id in the url and caption, url in request body as json.
[http://localhost:8081/memes/id](http://localhost:8081/memes/id "http://localhost:8081/memes/id")
CURL example:
```bash
curl --location --request PATCH 'http://localhost:8081/memes/1' \
--header 'Content-Type: application/json' \
--data-raw '{
	"caption":"updated caption",
	"url":"https://www.exterro.com/images/uploads/blogPosts/_1200x630_crop_center-center_82_none/Monkey-Puppet-Meme-LinkedIn.png?mtime=1601676585"
}'
```
5. Delete Meme - DELETE Method
[http://localhost:8081/memes/id](http://localhost:8081/memes/id "http://localhost:8081/memes/id")
CURL example:
```bash
curl --location --request DELETE 'http://localhost:8081/memes/1'
```
6. Swagger UI
[http://localhost:8081/swagger-ui/](http://localhost:8081/swagger-ui/ "http://localhost:8081/swagger-ui/")

## Running FrontEnd
1. Goto `\p-harikesh409-me_buildout_xmeme\frontend` and open `index.html`.
2. To change the backend URL update the `host` variable in `script.js` file located at `\p-harikesh409-me_buildout_xmeme\frontend\js`.
```js
// Change this value to update the backend URL
const host = "http://localhost:8081";
```
## Screenshots
### Home Page
[![Home Page](screenshots/homepage.png "Home Page")](screenshots/homepage.png "Home Page")
### Create Modal
[![Create Modal](screenshots/create.png "Create Modal")](screenshots/create.png "Create Modal")
### Update Modal
[![Update Modal](screenshots/update.png "Update Modal")](screenshots/update.png "Update Modal")
### Swagger UI
[![Swagger UI](screenshots/swagger-ui.png "Swagger UI")](screenshots/swagger-ui.png "Swagger UI")