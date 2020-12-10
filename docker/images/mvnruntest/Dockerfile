FROM maven:3.6.3-openjdk-11
COPY gamification-specs  gamification-specs
COPY test.sh test.sh
ENV URL=http://amtspringboot:8080
RUN ["chmod", "+x", "test.sh"]
ENTRYPOINT ["sh","test.sh"]

