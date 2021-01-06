FROM maven:3.6.3-openjdk-11
COPY ./. /
RUN chmod +x ./wait-for-it.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./wait-for-it.sh", "amtspringboot:8080", "--", "mvn", "verify"]
