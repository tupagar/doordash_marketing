# Problem Statement - A RESTFul API for the Doordash Drive merchant to upload customer phone numbers data to the server

## Steps to Run
### Prerequisites:
- Java 11

### To run the prebuilt runnable jar
```shell
java -jar executable/doordash_marketing-0.0.1-SNAPSHOT.jar

```
### To build and run the application on local
```shell
./gradlew bootRun
```
### To run the unit tests
```shell
./gradlew test
```

### How to clean the H2 DB for fresh runs?
```shell
./gradlew deleteDB
```

## Assumptions
- Phone-type can be either Home or Cell (matched case-insensitively). NO OTHER TYPES ALLOWED. THOSE ENTRIES WILL BE IGNORED
- Phone-type is always wrapped with the pair of brackets. Ex: (Home)
- Phone-number is in this fixed format = xxx-xxx-xxxx
- There won't be any spaces or characters in the phone-number
- API will simply ignore the malformed input phone numbers and will try its best to find valid numbers from the input.
- No error response if some or all of the entries in input are invalid
- Only the Application has access to the DB and no other user/system can do direct updates to the database. Making sure the data integrity.
  - Hence, this app doesn't do any further validations on the data read from the db.
- Application supports default concurrency levels supported by the tomcat application configured within

## Constraints
- Input raw phones input data can be maximum 1000 characters long. **TODO** - make the input size configurable at the time of startup
- API will return 403 bad input response if input "raw_phone_numbers" is either empty of more than 1000 characters

## Design
- I am using H2 DB with file persistence which helps us maintain the store across multiple service restarts.
- Database table "PHONE_INFO" for storing the phone details:
  - Attributes:
    - ID - long (auto generated)
    - PHONE_NUMBER - string
    - PHONE_TYPE - string
    - OCCURRENCES - long 
- Using DB Transaction Isolation level "Isolation.REPEATABLE_READ" to make sure that the concurrent transactions don't overwrite each other's data especially for occurrences values
