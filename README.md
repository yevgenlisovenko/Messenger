# Messenger API

## Messenger Service
- [Messenger Service](https://github.com/yevgenlisovenko/Messenger/tree/dev/messenger-service) is Java-based service which exposes Simple Messenger API.
- It utilizes Spring Boot framework and uses PostgreSQL to store a messages and query them.
- The service allows to its clients to send a short text messages and retrieve them via HTTP requests.

## Database
- PostgreSQL.
- There is one table `messages` and few indexes to speed up queries (see [sql script to initialize messenger DB](https://github.com/yevgenlisovenko/Messenger/blob/dev/db/create_tables.sql)).

## How to build and run Messenger Service
1. To build and run the project install using the links below:
    - [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
    - [Apache Maven 3.3+](https://maven.apache.org/download.cgi). Majke sure to follow [installation instructions](https://maven.apache.org/install.html).
    - [Docker](https://www.docker.com/products/docker-desktop/)
2. Clone the project https://github.com/yevgenlisovenko/Messenger
3. From the root folder of the project run the following command to build the service:\
  `mvn -f messenger-service/ clean install`
4. Then run `docker-compose build` to build Docker images.
5. Finally, run `docker-compose up` to start the containers.

## API
Once the service is running please use the following link to see its API:\
http://localhost:8085/swagger-ui/index.html

The API has three endpoints:
- `POST /v1/message`
- `GET  /v1/message/{recipient-id}/{sender-id}`
- `GET  /v1/message/{recipient-id}`

More details about how to use those endpoints see in the following sections.

### To send a short message from one user to another
```
curl --location --request POST 'http://localhost:8085/v1/message' \
    --header 'Content-Type: application/json' \
    --data-raw '{
      "senderId": "109",
      "recipientId": "500",
      "message": "pilot message!"
    }'
```
Notes:
- Request body has three fields `senderId`, `recipientId` and `message`
- All of them are required.
- `message` size should not exceed 1024 characters.
- The service validates those fields and request will be failed if validation fails.
- Successful response contains id of the posted message.
- Example of response:
    ```json
    {
        "messageId": 14
    }
    ```

### Get messages from particular sender
```
curl --location --request GET 'http://localhost:8085/v1/message/2001/1001?days=20&limit=4&from=6'
```
Notes:
- Recipient id and sender id are required and represent the part of URL (in this example 2001 is recipient id and 1001 is sender id).
- There are three **optional** query parameters which can be used in any combination:
  - `days` - Specifies for how many days messages needs to be returned. If not specified, this parameter controlled by service's property and by default equals to 30.
  - `limit` - Limits count of messages returned by response. If not specified, this parameter controlled by service's property and by default equals to 100.
  - `from` - Id of the message from which start search. Needed for pagination of messages.
- Example of response:
    ```json
    {
        "count": 4,
        "lastMessageId": 2,
        "messages": [
            {
                "id": 5,
                "dateTime": "2022-05-15T20:12:39.936853",
                "message": "Short message 139"
            },
            {
                "id": 4,
                "dateTime": "2022-05-15T19:12:39.935761",
                "message": "Short message 555"
            },
            {
                "id": 3,
                "dateTime": "2022-05-15T18:12:39.934837",
                "message": "Short message 703"
            },
            {
                "id": 2,
                "dateTime": "2022-05-15T17:13:39.933483",
                "message": "Short message 349"
            }
        ]
    }
    ```
- Note, that besides of messages response contains `lastMessageId` which can be used in subsequent requests to read next portion of messages.

### Get messages from all senders
```
curl --location --request GET 'http://localhost:8085/v1/message/2001'
```
```
curl --location --request GET 'http://localhost:8085/v1/message/2001?days=10&limit=1000&from=789'
```
Notes:
- Recipient id is required (in this example 2001 is recipient id).
- Three **optional** query parameters which can be used in any combination:
  - `days` - Specifies for how many days messages needs to be returned. If not specified, this parameter controlled by service's property and by default equals to 30.
  - `limit` - Limits count of messages returned by response. If not specified, this parameter controlled by service's property and by default equals to 100.
  - `from` - Id of the message from which start search. Needed for pagination of messages.
- The response for this endpoint groups messages by senders. Example of response:
```json
{
    "count": 6,
    "lastMessageId": 3,
    "messagesBySenders": [
        {
            "senderId": 1001,
            "messages": [
                {
                    "id": 6,
                    "dateTime": "2022-05-15T22:12:39.938184",
                    "message": "Short message 102"
                },
                {
                    "id": 5,
                    "dateTime": "2022-05-15T20:12:39.936853",
                    "message": "Short message 139"
                },
                {
                    "id": 4,
                    "dateTime": "2022-05-15T19:12:39.935761",
                    "message": "Short message 555"
                },
                {
                    "id": 3,
                    "dateTime": "2022-05-15T18:12:39.934837",
                    "message": "Short message 703"
                }
            ]
        },
        {
            "senderId": 1002,
            "messages": [
                {
                    "id": 8,
                    "dateTime": "2022-04-17T17:12:39.941728",
                    "message": "Short message 422"
                },
                {
                    "id": 7,
                    "dateTime": "2022-05-14T17:12:39.940073",
                    "message": "Short message 937"
                }
            ]
        }
    ]
}
```
- Note, that besides of messages response contains `lastMessageId` which can be used in subsequent requests to read next portion of messages.