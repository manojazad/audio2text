# Audio2Text
A Spring boot rest api which convert a given audio file to text

### Supported Audio File

As of now it only supporting **.wav** file format

### How to run

Build the project using maven to create jar file
```
$ mvn clean install
```

and then run the newly created jar file

```
$ java -jar target/assignment-1.0-SNAPSHOT.jar

```

## About Api

```
API for speech to text
```
*  Method: *POST*
*  End Point : *http://127.0.0.1:4230/audio/${id}*
* Path Param: *${id} is the identifier of the uploaded file*

```
API to find text on given id
```
*  Method: *GET*
*  End Point : *http://127.0.0.1:4230/audio/${id}*
* Path Param: *${id} is the identifier of the uploaded file*

### Configuration

Following properties are exteranalize using application.properties file.

```
server.port
spring.data.mongodb.host
spring.data.mongodb.port
bing.subscription.key
```
