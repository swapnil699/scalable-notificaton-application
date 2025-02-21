# Scalable Notification System

## Introduction
The Scalable Notification System is designed to handle high-throughput notifications across multiple channels, including Email, SMS, and Push Notifications. The system leverages Apache Kafka for event streaming, Redis for caching, and third-party providers such as Twilio for SMS delivery. It prioritizes messages efficiently and ensures real-time communication for modern applications.

## Features
- **Notification API**: Create and manage notifications across different channels.
- **Multi-Channel Support**: Supports Email, SMS, and Push Notifications with extendability for more.
- **Priority-Based Queuing**: Dynamically processes notifications based on priority levels (Transactional, Informational, Promotional).
- **User Preferences**:
  - Opt-in/out of specific channels or categories.
  - Set quiet hours for each channel.
- **Duplicate Prevention**: Avoids sending duplicate notifications using a UNIQUE DB constraint and message hashing.
- **Templating Support**: Allows placeholders like `{name}` and `{otp}` for dynamic message personalization.
- **Rate Limiting**: Efficiently handles third-party vendor API limits.
- **Scalability**: Asynchronous processing ensures high performance and non-blocking operations.

## System Requirements
- Java Development Kit (JDK)
- MySQL Database
- Apache Kafka
- Twilio Account for SMS

## Configuration
### Application Properties
Each service requires its own configuration file to connect to Kafka, Redis, and other external resources. Update the `application.properties` file in the following services:
- `notificationservice`
- `EmailConsumer`
- `SmsConsumer`

Ensure correct Kafka and Redis connection details are provided.

### MySQL Setup
```properties
# Create and update the database tables automatically
spring.jpa.hibernate.ddl-auto=update

# Database connection details
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/notification_system
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Show SQL statements in logs
spring.jpa.show-sql=true
```

### Kafka Setup
#### Start Zookeeper and Kafka Server
```sh
# Tab 1 - Start Zookeeper
C:\kafka> bin\windows\zookeeper-server-start.bat config\zookeeper.properties

# Tab 2 - Start Kafka Server
C:\kafka> bin\windows\kafka-server-start.bat config\server.properties
```
#### Create Kafka Topics
```sh
# Tab 3 - Create required Kafka topics
C:\kafka\bin\windows> kafka-topics.bat --create --topic priority-1 --bootstrap-server localhost:9092
C:\kafka\bin\windows> kafka-topics.bat --create --topic priority-2 --bootstrap-server localhost:9092
C:\kafka\bin\windows> kafka-topics.bat --create --topic priority-3 --bootstrap-server localhost:9092
C:\kafka\bin\windows> kafka-topics.bat --create --topic broadcast-offer --bootstrap-server localhost:9092
C:\kafka\bin\windows> kafka-topics.bat --create --topic sms-notifications --bootstrap-server localhost:9092
```

### Twilio Setup
1. Create a Twilio account and obtain API credentials.
2. Add dependencies in `pom.xml`.
3. Configure Twilio in `application.properties`:
```properties
twilio.account.sid=your_actual_account_sid
twilio.auth.token=your_actual_auth_token
twilio.phone.number=your_twilio_phone_number
```

## API Endpoints
### Send Notification
**POST** `http://localhost:8080/api/send-notification`
#### Request Body
```json
{
  "notificationPriority": 1,
  "channels": ["email", "push"],
  "recipient": {
    "userId": "48648",
    "userEmail": "sbhosale17599@gmail.com"
  },
  "content": {
    "usingTemplates": false,
    "templateName": "",
    "placeholders": {},
    "message": "Hello, this is a test notification!",
    "emailSubject": "Test Notification",
    "emailAttachments": [],
    "pushNotification": {
      "title": "Test Push",
      "action": {
        "url": "https://example.com"
      }
    }
  }
}
```
#### Response
```json
{"message": "Notification accepted for processing."}
```
![Screenshot 2025-02-21 202524](https://github.com/user-attachments/assets/b3974d82-38b1-427b-822d-53393c3c1b09)
![Screenshot 2025-02-21 204530](https://github.com/user-attachments/assets/93105cb3-a625-4ecc-b9f1-60ff4d476437)
![Screenshot 2025-02-21 204656](https://github.com/user-attachments/assets/4c56b7af-210e-456b-9c7e-b883c237521d)



### Broadcast Offers
**POST** `http://localhost:8080/api/broadcast-offer`
#### Request Body
```json
{
  "emailSubject": "Limited Time Offer!",
  "message": "Get 50% off on your next purchase. Hurry up!"
}
```
#### Response
```json
{"message": "Broadcast offer accepted for processing."}
```
![Screenshot 2025-02-21 202948](https://github.com/user-attachments/assets/8c11069b-7bca-4d2b-884c-9f989ca494b6)
![Screenshot 2025-02-21 204824](https://github.com/user-attachments/assets/b906659d-3661-44dd-91a2-59d740bd8ea0)
![Screenshot 2025-02-21 204912](https://github.com/user-attachments/assets/dc47ea8c-764d-43b7-af37-ab6193b3b569)


### Send SMS
**POST** `http://localhost:8080/api/send-sms`
#### Request Body
```json
{
  "phoneNumber": "+919146XXXXXX",
  "messageBody": "Hello, this is a test message!"
}
```
#### Response
```json
{"message": "SMS request received successfully."}
```
![Screenshot_2025-02-21-20-31-37-63_0ce57feeccaa51fb7deed04b4dbda235](https://github.com/user-attachments/assets/3c2d45e2-23c6-46a4-8e69-6148c3c42158)


## Contributors
- Swapnil Bhosale

## Contact
For queries or contributions, reach out to [sbhosale17599@gmail.com](mailto:sbhosale17599@gmail.com).

