FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN gradle build
ADD ./build/libs/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app.jar"]
