FROM openjdk:17
RUN mkdir /app
COPY /target/crm-0.0.1-SNAPSHOT.jar /app/crm.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "crm.jar"]