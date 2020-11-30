FROM openjdk:11
COPY *.jar api.jar
ENV SPRING_BOOT_PORT=8080
ENTRYPOINT ["java","-jar","/api.jar"]
