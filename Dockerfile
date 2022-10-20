FROM openjdk:11-jre-slim

ENV APP_HOME=/app

ADD ./oohub-be/build/libs/oohub-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "oohub-0.0.1-SNAPSHOT.jar"]