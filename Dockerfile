# Base 이미지로 OpenJDK 17 사용
FROM openjdk:17

# Tomcat 버전 설정 (필요한 경우)
ENV TOMCAT_VERSION=9.0.82

# JAR 파일 복사 (빌드한 JAR 파일을 이미지 내 /test.jar 위치에 복사)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /test.jar

# 타임존 설정 (선택 사항)
ENV TZ=Asia/Seoul

# 애플리케이션 시작 명령 (test.jar를 실행)
ENTRYPOINT ["java", "-jar", "/test.jar"]
