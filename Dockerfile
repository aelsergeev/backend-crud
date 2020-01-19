FROM openjdk:11
WORKDIR /opt

ARG KTOR_JAR_FILE=/build/libs/ktor*.jar
ARG SPRING_JAR_FILE=/build/libs/spring*.jar

COPY $KTOR_JAR_FILE ktor.jar
COPY $SPRING_JAR_FILE spring.jar
COPY docker.sh docker.sh

ENTRYPOINT ./docker.sh
