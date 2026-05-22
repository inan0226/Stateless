# 1. Base Image: 자바 실행 환경
FROM eclipse-temurin:17-jdk-alpine

# 2. 빌드된 jar 파일의 경로를 변수로 설정 (아래는 Gradle 기준)
ARG JAR_FILE=build/libs/*.jar

# 3. 호스트(내 컴퓨터)의 jar 파일을 컨테이너 내부로 복사
COPY ${JAR_FILE} app.jar

# 4. 컨테이너 실행 시 작동할 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]