# ShoppertiseNote
Shoppertise Note simple Restful API

### Project start
1. Install mongodb and execute the command in mongosh from the file to create the database and collection.
```
src/main/resources/db_init
```
2. Insert data using mongoimport from the file.
```
mongoimport --db shoppertise_notes --collection note < src/main/resources/note.json
mongoimport --db shoppertise_notes --collection user < src/main/resources/user.json
```
3. Execute the command in the main directory to start the app.
```
mvn spring-boot:run
```
### Authentication required
```
username: user
password: password
```

### Test with swagger
```
http://localhost:8080/swagger-ui/
```
### May refer the table below to test with Postman, authorization with Basic Auth 
#### url ```http://localhost:8080/shoppertise/...```
### API
|HTTP Verb|CRUD|API|Specific Item|Purpose|Authentication|
|---------|----|---|-------------|-------|--------------|
|POST|Create|/shoppertise/addNote| passing json object (refer below)|Create new note|Required|
|GET|Read|/shoppertise/getAllNotesByGroup|passing **sortBy** [asc or desc]|Fetch note group by tags|Required|
|GET|Read|/shoppertise/getAllNotesByPage|passing **page** [number] and **sortBy** [title, content or tags]|Fetch note by page|Required|
|PUT|UPDATE|/shoppertise/note/{title}|passing the **title** in a string format|Update note such as title, content or tags|Required|
|DELETE|Delete|/shoppertise/remove/{title}|passing the **title** in a string format|Remove note|Required|
|GET|Read|/shoppertise/status/| -|Get the app status|-|

### JSON Object for POST method
```
{
  "title": "Sample Title",
  "content": "Smaple title content",
  "tags": ["Tag1", "Tag2"]
}
```

### Technology used
1. Spring boot 2.6.6
2. Maven
3. Java 11
4. Mongodb
5. Spring security
6. Spring AOP
7. Spring dev tool
8. Jackson datatype
9. Spring fox (SwaggerUI2)
10. Log4j2
