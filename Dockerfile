FROM openjdk:17-jdk-slim-buster
LABEL authors="omocha"

# 애플리케이션 JAR 파일을 컨테이너 내로 복사
COPY build/libs/omocha-api.jar /docker-springboot.jar

# ENTRYPOINT를 수정하여 prod 프로파일 활성화
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/docker-springboot.jar", "-Duser.timezone=Asia/Seoul"]