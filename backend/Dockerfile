FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./build/libs/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app.jar", "-Dkms.url=ws://3.34.94.178:8888/kurento"]
