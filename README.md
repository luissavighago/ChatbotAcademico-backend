# Stage AI - API
This project was the backend developed for a Final Graduation Project (TCC). The objective of this work was to develop an academic *chatbot* that acted as a virtual assistant for users (students, professors, and course coordinators), helping them understand the requirements, procedures, and guidelines related to the internship regulations of the Federal University of Technology - Paraná (UTFPR).

This project integrates with the OpenAI API to provide intelligent and context-aware responses to user queries, enhancing the chatbot's ability to assist users effectively.

## Technologies Used

- Java
- Spring Boot
- Maven
- Spring AI

## Project Structure

- `src/main/java/com/chatbot/chatbot/controller`: Contains the controllers for handling API requests.
- `src/main/java/com/chatbot/chatbot/dto`: Contains the Data Transfer Objects (DTOs) used in the application.
- `src/main/java/com/chatbot/chatbot/enums`: Contains the enums used in the application.
- `src/main/java/com/chatbot/chatbot/exception`: Contains custom exceptions.
- `src/main/java/com/chatbot/chatbot/models`: Contains the entity models.
- `src/main/java/com/chatbot/chatbot/repository`: Contains the repository interfaces.
- `src/main/java/com/chatbot/chatbot/service`: Contains the service classes for business logic.

## Environment Variables

To run the project, you need to define the following environment variables:

```properties
spring.ai.openai.api-key=${OPENAI_KEY}
spring.datasource.url=${PG_URL}
spring.datasource.password=${PG_PASSWORD}
spring.datasource.username=${PG_USER}
```

## Endpoints

### Prompts Controller

- **POST /prompts/ask**
  - Description: Asks a question using different prompts and returns a list of responses.
  - Request Body: `QuestionRecordDTO`
  - Responses:
    - `200`: Success
    - `400`: Invalid parameters
    - `422`: Invalid request data
    - `500`: Error processing the request

### Assistant Controller

- **POST /chatbot/ask**
  - Description: Asks a question to start a conversation.
  - Request Body: `QuestionRecordDTO`
  - Responses:
    - `200`: Success
    - `400`: Invalid parameters
    - `422`: Invalid request data
    - `500`: Error processing the request

- **PUT /chatbot/evaluate-answer/{id}**
  - Description: Evaluates the chatbot's answer.
  - Path Variable: `id` (UUID)
  - Request Body: `EvaluateAnswerRecordDTO`
  - Responses:
    - `200`: Success
    - `400`: Invalid or missing parameters
    - `422`: Invalid request data
    - `500`: Error processing the evaluation
