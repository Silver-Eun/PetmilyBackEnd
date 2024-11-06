FROM openjdk:17

ENV TOMCAT_VERSION=9.0.82

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} ./test.jar

# JSP 파일 복사
COPY src/main/webapp /app/src/main/webapp

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "./test.jar"]
