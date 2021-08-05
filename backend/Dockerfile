FROM openjdk:8-jdk-alpine

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
#RUN gradle cleanQuerydslSourcesDir
RUN chmod +x ./gradlew
#RUN ./gradlew build --stacktrace
#RUN ./gradlew bootjar

FROM openjdk:8-jdk-alpine
COPY --from=builder build/libs/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app.jar", "-Dkms.url=ws://3.34.94.178:8888/kurento"]
