# notification
Send Reddit Notification to subscribed User

# High Level Design of Application

![](notification.jpg) 


#Resources 

- Create User Account
```java
http://localhost:8081/api/user/create/
```
-Request Format
```json
{
	"fullName":"Umesh Verma",
	"emailId":"TestEmail@gmail.com",
	"countryCode": "DE",
	"notificationStatus": "SUBSCRIBED"
}
```

- Get User Detail
```java
http://localhost:8081/api/user/fetch/{emailId}
```
-Response Format
```json
{
    "userId": "dd178821-15c6-45c2-9b97-7df4d1ce73d1",
    "emailId": "TestEmail@gmail.com",
    "notificationStatus": "SUBSCRIBED"
}
```

# Assumption

