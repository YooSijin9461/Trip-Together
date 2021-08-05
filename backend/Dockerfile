FROM openjdk:8-jdk-alpine
#VOLUME /tmp

RUN echo $ls
RUN chmod +x gradlew
RUN ./gradlew clean build

# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle .
# COPY settings.gradle .
# COPY src src
#RUN gradle cleanQuerydslSourcesDir

#RUN ./gradlew build --stacktrace
#RUN ./gradlew bootjar

#COPY --from=builder build/libs/*.jar app.jar

ADD ./build/libs/*.jar app.jar
#ENV JAVA_OPTS="-Dkms.url=ws://3.34.94.178:8888/kurento"
ENTRYPOINT ["java", "-jar", "/app.jar"]

