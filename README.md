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
docker  build . -t newtonxtweeter
```
 
## Run

```
docker run -p 8080:8080 newtonxtweeter
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

 
 

