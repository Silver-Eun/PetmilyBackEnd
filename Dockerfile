FROM openjdk:17

ENV TOMCAT_VERSION=9.0.82

# JAR 파일 경로 설정
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} ./test.jar

# JSP 파일 복사 (경로 수정)
COPY src/main/webapp/WEB-INF /app/src/main/webapp/WEB-INF

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "./test.jar"]
