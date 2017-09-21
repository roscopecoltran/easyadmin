## frontend

### react redux-form material-ui 
```
src/main/app 

npm install & npm start
```

## backend

### spring boot

```
Application -> run
```

## demo

http://101.132.97.131

## architecture
![architecture](https://github.com/data-server/easyadmin/blob/master/screenshots/architecture.png?raw=true)

## related projects
### [admin-on-rest](https://github.com/marmelab/admin-on-rest)
### [jwt-spring-security-demo](https://github.com/szerhusenBC/jwt-spring-security-demo)
### [json-api](https://github.com/json-api/json-api)
### [parse-server](https://github.com/parse-community/parse-server)


## RESTful APIs

### Overview
### Version information
Version: 1.0

### URI scheme
Host: localhost:8080
BasePath: /

### Tags

#### data-controller: Data Controller
* ![create record](https://github.com/data-server/easyadmin/wiki/paths#datamutation)
* ![query records with filter,page,sort](https://github.com/data-server/easyadmin/wiki/paths#dataquery)
* ![edit record](https://github.com/data-server/easyadmin/wiki/paths#datamutation-1)
* ![find one](https://github.com/data-server/easyadmin/wiki/paths#findone)


#### authentication-rest-controller: Authentication Rest Controller
* ![login and return token](https://github.com/data-server/easyadmin/wiki/paths#createauthenticationtoken)
* ![refresh token](https://github.com/data-server/easyadmin/wiki/paths#refreshandgetauthenticationtoken)


#### user-controller: User Controller

#### apply-controller: Apply Controller

#### role-controller: Role Controller

#### schema-controller: Schema Controller(for all the login user)
* ![edit entity](https://github.com/data-server/easyadmin/wiki/paths#editentity)
* ![add field](https://github.com/data-server/easyadmin/wiki/paths#addfield)
* ![find all fields](https://github.com/data-server/easyadmin/wiki/paths#findallfields)
* ![find one entity](https://github.com/data-server/easyadmin/wiki/paths#findone-1)
* ![edit one field](https://github.com/data-server/easyadmin/wiki/paths#editfield-1)
* ![add entity](https://github.com/data-server/easyadmin/wiki/paths#addentity)
* ![get schemas for current login user](https://github.com/data-server/easyadmin/wiki/paths#getschemas)


#### permission-controller: Permission Controller (only for ROLE_ADMIN users)
* ![addpermission](https://github.com/data-server/easyadmin/wiki/paths#addpermission)
* ![list all the permissions](https://github.com/data-server/easyadmin/wiki/paths#list)
* ![edit the permissions](https://github.com/data-server/easyadmin/wiki/paths#editfield)
* ![find one permission](https://github.com/data-server/easyadmin/wiki/paths#finduser)
