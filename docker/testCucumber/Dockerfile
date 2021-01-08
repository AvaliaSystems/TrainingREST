FROM maven

ENV SPRING_BOOT_PORT=8080
COPY wait-for-it.sh wait-for-it.sh
COPY gamification-specs/ ./
RUN chmod +x wait-for-it.sh

#ENTRYPOINT ["./wait-for-it.sh", "api:8080", "--", "mvn", "test", "-Dmaven.test.error.ignore=false"]
ENTRYPOINT ["./wait-for-it.sh", "api:8080", "--", "mvn", "clean", "verify"]
