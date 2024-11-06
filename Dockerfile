# Base 이미지로 OpenJDK 17 사용
FROM openjdk:17

# Tomcat 버전 설정 (필요한 경우)
ENV TOMCAT_VERSION=9.0.82

# JAR 파일 복사 (빌드한 JAR 파일을 이미지 내 /test.jar 위치에 복사)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /test.jar

# JSP 파일 복사 (src/main/webapp 디렉토리 내 모든 파일을 컨테이너의 /app/src/main/webapp으로 복사)
COPY src/main/webapp/WEB-INF/views /app/src/main/webapp/WEB-INF/views

# 타임존 설정 (선택 사항)
ENV TZ=Asia/Seoul

# 애플리케이션 시작 명령 (test.jar를 실행)
ENTRYPOINT ["java", "-jar", "/test.jar"]