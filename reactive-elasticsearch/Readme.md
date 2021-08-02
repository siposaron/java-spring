# Reactive Elastic App

Sample reactive spring boot app with elastic search repository.

Features:
- dto validation
- reactive controllers
- reactive elasticsearch repository
- full text search on course and related author

Future work:
- pagination
- update, delete courses
- dockerize app, extend compose file

## Local run

Start the Elasticsearch and Kibana from the project root folder via 

`docker-compose up`

Build and run the spring application with maven

`./mvnw clean install`

`./mvnw spring-boot:run`

Or simply start the app from you favourite IDE.

## Testing

Import the `Elastic-Spring.postman_collection.json` into Postman and run the request against your local app.