#FROM openjdk:8-jdk-alpine
FROM adoptopenjdk:8-jdk-hotspot AS builder
VOLUME /tmp
#COPY gradlew ./

#RUN echo $ls
#RUN ./gradlew clean build

COPY . .

#COPY gradlew .
#COPY gradle gradle
#COPY build.gradle .
#COPY settings.gradle .
#COPY src src
#RUN gradle cleanQuerydslSourcesDir

RUN chmod +x ./gradlew
#RUN ./gradlew build --stacktrace
RUN ./gradlew bootjar

FROM adoptopenjdk:8-jdk-hotspot
COPY --from=builder build/libs/*.jar app.jar

#ADD ./build/libs/*.jar app.jar
#ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-Dspring-boot.run.jvmArguments", "-Dkms.url=ws://3.34.94.178:8888/kurento", "-Dserver.address=0.0.0.0", "-jar", "/app.jar"]

