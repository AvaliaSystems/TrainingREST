FROM openjdk:12
COPY *.jar api.jar
ENV SPRING_BOOT_PORT=8080
ENTRYPOINT ["java","-jar","/api.jar"]
