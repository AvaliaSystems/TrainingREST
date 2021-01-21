FROM ghcr.io/gusamaal/gamification-engine-amt/gamificationenginetest:latest

# Add wait-for-it
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh" , "amtdb:3306" , "--strict" , "--timeout=300" , "--" , "java","-jar","/app.jar"]
