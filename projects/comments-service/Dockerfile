FROM openjdk:8-jdk

ENV JAVA_OPTS="-Xms256 -Xmx256"

COPY target/comments-service-0.0.1-SNAPSHOT.jar /app.jar
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
