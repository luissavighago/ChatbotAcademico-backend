FROM eclipse-temurin:23

COPY ./target/chatbot-0.0.1-SNAPSHOT.jar chatbot-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "chatbot-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080