# Xelba-POC
POC for the upcoming Xelba product.
* Written in JAVA.
* Built using Maven.
* Uses Glassfish Jersey for REST implementation (both client and server).
* Uses Eclipse persistence (JPA implementation) to connect with underlying MySQL db.
* Uses JJWT for JSON Web Token implementation.

### Prerequisites
1. Maven is required to build the project war.
2. Tomcat is required to deploy the war and run the project.
3. Postman is required to make rest calls and send proper data in request.

### URL information
1. For authentication requests URL will be:
```
http://localhost:8080/XelbaMySQLRest/rest/token/<request endpoint>
```

2. For employee CRUD (Create-Retrieve-Update-Delete) operations URL will be:
```
http://localhost:8080/XelbaMySQLRest/rest/employee/<request endpoint>
```

## Logging information
Logs are generated inside the tomcat webapps folder at :
```
$CATALINA_HOME/webapps/logs/
```

## User permission information:
1. HR can perform: CRU
2. Manager can perform: RU
3. Exit team can perform: RD
4. Other users can only retrieve

## Authors and contributors
**Anant Chaturvedi** - *Java development*