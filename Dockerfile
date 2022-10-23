FROM ubuntu

RUN apt update -y
RUN apt install vim -y

# java 11
RUN apt-get update
RUN apt-get install software-properties-common -y
RUN apt-get install -y openjdk-11-jdk

# python 3.9
RUN apt install python3
RUN apt install python3-pip -y


ENV APP_HOME=/app

ADD ./oohub-be/src/run.py .
ADD ./oohub-be/build/libs/oohub-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "oohub-0.0.1-SNAPSHOT.jar"]