FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./build/libs/*.jar app.jar
RUN gradle build
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app.jar"]
