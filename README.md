# Newtonx twitter stream


## About 

Couple things to know : 
- This project needs twitter API keys. You need to add your keys in the file twitter4j.properties before building.
- This spring boot application has an embedded hsql database that starts with the web app. In a real production application we would want the database to be separated from the web app and be in its own docker image.

## Build

```
mvn clean install package
```

```
docker  build . -t newtonxtwitter
```
 
## Run

```
docker run -p 8080:8080 newtonxtwitter
```

## Test

### Signup flow

```
TOKEN=$(curl 'localhost:8080/signup?username=test&password=test')
curl --header "Authorization:Bearer $TOKEN" localhost:8080/tweets
```

### Signin flow

```
TOKEN=$(curl 'localhost:8080/signin?username=test&password=test')
curl --header "Authorization:Bearer $TOKEN" localhost:8080/tweets
```

## Future improvements (out of scope)

### Better testing. 
We should have unit tests of the different classes and tests that call the API and verify the responses

### Separated DB
We should have a DB separated from the app that doesn't live in memory and is persisted on the disk.

### Error handling
For now, I just let spring do the mapping between the exceptions and the http error codes. Ideally we should define our own exceptions and map then to http codes. 

 
 

