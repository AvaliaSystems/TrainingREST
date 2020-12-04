FROM openjdk:11

ENV SPRING_BOOT_PORT=8080
COPY *.jar api.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "impl_db:3306", "--", "java", "-jar","/api.jar"]
