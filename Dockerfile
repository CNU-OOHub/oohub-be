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

# 한글 설정
RUN apt-get update && apt-get install -y \
    language-pack-ko \
    fonts-nanum \
    fonts-nanum-coding

# 언어 설정
RUN locale-gen ko_KR.UTF-8
ENV LANG ko_KR.UTF-8
ENV LANGUAGE ko_KR.UTF-8
ENV LC_ALL ko_KR.UTF-8


ENV APP_HOME=/app

ADD ./oohub-be/src/run.py .
ADD ./oohub-be/build/libs/oohub-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "oohub-0.0.1-SNAPSHOT.jar"]