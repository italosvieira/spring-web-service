# Demo Spring Boot Project

Demo project of a secure RESTful web service with a CRUD architecture.

## Getting Started
Send a POST request to http://localhost:8080/api/auth/authenticate to get a token.

Administrator token.
```
{
    "login": "administrator@email.com",
    "password": "administrator"
}
```

Regular user token token.
```
{
    "login": "user0@email.com",
    "password": "user0"
}
```
Test your permissions at http://localhost:8080/api/private/users

## Build
```
Maven: mvn clean install
```
## Run
```
Maven: spring-boot:run
Java: Application.java
```
  
## Architecture
```
main
    ├───java
    │   └───com
    │       └───springwebservice
    │           │   Application.java
    │           │   ApplicationDataBaseLoader.java
    │           │   
    │           ├───controller
    │           │       HomeController.java
    │           │       UserController.java
    │           │       
    │           ├───core
    │           │   ├───abstraction
    │           │   │   │   CrudControllerAbstraction.java
    │           │   │   │   CrudCustomRepositoryAbstraction.java
    │           │   │   │   CrudServiceAbstraction.java
    │           │   │   │   SecurityControllerAbstraction.java
    │           │   │   │   
    │           │   │   └───interfaces
    │           │   │           CrudServiceInterface.java
    │           │   │           
    │           │   ├───exception
    │           │   │       BusinessException.java
    │           │   │       SecurityException.java
    │           │   │       
    │           │   ├───handler
    │           │   │       GenericExceptionHandler.java
    │           │   │       
    │           │   └───utils
    │           │           ApiUtils.java
    │           │           PropertyUtils.java
    │           │           SecurityUtils.java
    │           │           
    │           ├───entity
    │           │       PermissionEntity.java
    │           │       RoleEntity.java
    │           │       RolePermissionEntity.java
    │           │       UserEntity.java
    │           │       UserRoleEntity.java
    │           │       
    │           ├───repository
    │           │   │   PermissionRepository.java
    │           │   │   RolePermissionRepository.java
    │           │   │   RoleRepository.java
    │           │   │   UserRepository.java
    │           │   │   UserRoleRepository.java
    │           │   │   
    │           │   └───custom
    │           │       │   UserRepositoryCustomImpl.java
    │           │       │   
    │           │       └───interfaces
    │           │               UserRepositoryCustom.java
    │           │               
    │           ├───security
    │           │   │   AuthenticationLoginFilter.java
    │           │   │   AuthenticationValidatorFilter.java
    │           │   │   JwtAuthenticationService.java
    │           │   │   WebSecurityConfig.java
    │           │   │   
    │           │   └───model
    │           │           LoginModel.java
    │           │           UserAuthenticationModel.java
    │           │           
    │           └───service
    │               │   AuthenticationServiceImpl.java
    │               │   PermissionServiceImpl.java
    │               │   RolePermissionServiceImpl.java
    │               │   RoleServiceImpl.java
    │               │   UserRoleServiceImpl.java
    │               │   UserServiceImpl.java
    │               │   
    │               └───interfaces
    │                       AuthenticationService.java
    │                       PermissionService.java
    │                       RolePermissionService.java
    │                       RoleService.java
    │                       UserRoleService.java
    │                       UserService.java
    │                       
    └───resources
            application.properties
```

## Generating Asymmetric Private Key
* For this demo project the AsymmetricPrivateKey.pem is inside the resource folders, but for real projects it should not be.
```
openssl genrsa -aes256 -out AsymmetricKey.pem 2048

openssl pkcs8 -topk8 -inform PEM -outform PEM -in AsymmetricKey.pem -out AsymmetricPrivateKey.pem -nocrypt
```

## Built With
* [Spring](https://spring.io/) - Web Framework.
* [Spring Boot](https://spring.io/projects/spring-boot) - Spring project to create spring-powered applications.
* [Project Lombok](https://projectlombok.org/) - Project Lombok is a java library to generate java code.
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/) - Lang provides a host of helper utilities for the java.lang API.
* [H2 Database](http://www.h2database.com/html/features.html) - In-memory database.
* [JJWT](https://github.com/jwtk/jjwt) - JSON Web Token for Java and Android.
* [Maven](https://maven.apache.org/) - Dependency Management.