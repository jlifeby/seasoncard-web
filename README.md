# SeasonCard WEB
## Demo deployment: [seasoncard.by](http://seasoncard.by)
## Required environment:
    * Java >=8
    * MongoDB >= 3
    * Gradle >= 2.8 or use gradle wrapper
> **Attention:**
If you have problems with developpping from IDEA please see file system.properties in resources directory for web module

# Configuring Environment

# Building and deploying
## Restore db for Dev
```
mongorestore --drop db/dev
```
    
## Restore db for QA
```
mongorestore --drop db/qa
```
    
## Build project:
```
./gradlew clean build
```
    
## Build project without test: for dev environment. Will be used db kartka_dev
```
./gradlew clean build -x test
```
    
## Build for specific environment:
```
./gradlew clean build -PenvType=qa -x test // for qa
./gradlew clean build -PenvType=dev -x test // for dev
./gradlew clean build -PenvType=beta -x test // for beta
```

## To set upload dir please add build property uploadDir
```
./gradlew clean build -PenvType=dev -PuploadDir=/kartkaFiles -x test
```

## Build with all parameters
```
./gradlew clean build -PenvType=dev -PuploadDir=/kartkaFiles  -PemailNoreplyPassword=... -PsmsRocketPassword=... -PsmsAssistantPassword=... -PsmsInfobipPassword=... -PmongoCreds=... -x test 
```

Default value for upload dir is {user.home}/kartka_upload/{envType}/
    
Copy built war from ./web/build/lib to tomcat webapps directory as ROOT.war

## Test users

* company@kartka.by/1111 with role ROLE_COMPANY
* admin@kartka.by/1111 with ROLE_ADMIN
* user@kartka.by/1 or 1/1 with ROLE_USER and card UUID 1

## Demo users
* demo@kartka.by/demo with ROLE_COMPANY
* 101/101 - user with ROLE_USER and card UUID 101


Virtual Cards:
for Demo: 110 - 115
for Company test: 10 - 15

## Explore API using Swagger UI
via http://localhost:8080/swagger-ui.html
