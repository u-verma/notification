# Notification Application

- Send Reddit Notification Email

# Start application

- run the `docker-compose` available in the root directory. 
- To start run below command
```
docker-compose up -d
```
- To Stop run below
```
docker-compose down
```
- Either you can remove the volume path from `docker-compose` but data will be lost if container is stopped, Or change it to your local path.
- Then run the application directly from your id Application start at 8081 port.

# High Level Design of Application

![](notification.jpg) 

# User recieve below Email 

![](Template.png)

# Resources 

# ******** User API ********

- Create User Account 
- URL
```URL
http://localhost:8081/api/user/create/
```
- PUTRequest
```json
{
	"fullName":"Umesh Verma",
	"emailId":"TestEmail@gmail.com",
	"countryCode": "DE",
	"notificationStatus": "SUBSCRIBED"
}
```

# Add User profile 

- GETRequest with Path Param {emailId}
- URL
```URL
http://localhost:8081/api/user/fetch/{emailId}
```
- Response Format : Please Note UserId is essential for further API call
```json
{
    "userId": "dd178821-15c6-45c2-9b97-7df4d1ce73d1",
    "emailId": "TestEmail@gmail.com",
    "notificationStatus": "SUBSCRIBED"
}
```

# Enable Subscription for USER by default it is enabled

- URL 
```URL
http://localhost:8081/api/user/enableSubscription
```
- PUTRequest
```json
{ 
    "userId": "8b065a35-b305-49e9-b601-2e7ecbe84f4f"
}
```

# Disable Subscription for USER

- URL 
```URL
http://localhost:8081/api/user/disableSubscription
```
- PUTRequest
```json
{ 
    "userId": "8b065a35-b305-49e9-b601-2e7ecbe84f4f"
}
```

# ******** Subscription API for USER: ********

# Add channels. By default, notification is enabled for USERS. Use user API to disable.

- URL 
```URL
http://localhost:8081/api/subscribe/addChannel
```
- PUTRequest
```json
{
    "userId": "dd178821-15c6-45c2-9b97-7df4d1ce73d1",
    "redditChannel": "technology"
}
```

# Update user channel: 

- This API removes all the old subscribed channels and Add new updated channel.

- URL 
```URL
http://localhost:8081/api/subscribe/updateChannel
```
- PUTRequest
```json
{
    "userId": "dd178821-15c6-45c2-9b97-7df4d1ce73d1",
    "redditChannel": "technology"
}
```

# ******** Email Notification  ********

# send Email notification to user at any time.

- This API will send the email to list of user provided they have notification enabled and have valid channel subscribed. 

- URL 
```URL
http://localhost:8081/api/email/send
```
- PUTRequest
```json
  [
    "dd178821-15c6-45c2-9b97-7df4d1ce73d1",
    "8b065a35-b305-49e9-b601-2e7ecbe84f4f",
    "97095a32-h602-47b2-l205-6h0ecke24j4j"
    ]
    
```

# Assumption

- Application will be Running in UTC TimeZone
- I will be having some Api to identify Users Local TimeZone and equivalent UTC time.
- User always sending correct Channel names during Adding channel, no validation done for checking valid channel names.
- No failure in third party system (Ahh!!! this is big one)

# Improvement Required in current implementation
- Proper exception handling.
- Proper User response during exception in API.
- Using Circuit breaker during calling third party API.
- Fault tolerance, handle failure scenario.
- Retry logic during failure.
- Transaction handling.
- Code refactoring. 
- Proper Logging for debugging.
- Monitoring and health check dashboard the API.
- API documentation as code.
- Executor service to send email in parallel to the USER.
- Adding Integration test.
- Add more Unit Test.
