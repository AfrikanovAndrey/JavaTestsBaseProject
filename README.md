### Base project for writing API & Web tests on Java with using technologies:
 - Maven (project management)
 - JUnit 5 (test framework)
   - Parametrized tests
   - Extension usage 
 - RestAssured (API test framework)
 - Selenide (Web test framework)
 - Allure (reporting)  


##### Run tests

```
mvn clean test
```

##### Build report

To build common report (both API & Web)
```
allure serve api-tests/target/allure-results web-tests/target/allure-results
```
P.S. Allure command-line has been installed

