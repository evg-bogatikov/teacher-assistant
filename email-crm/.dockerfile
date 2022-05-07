FROM openjdk:16-alpine
RUN mkdir -p "/app/"
ARG JAR_FILE=build/libs/email-crm-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} /app/email-crm-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/email-crm-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081
