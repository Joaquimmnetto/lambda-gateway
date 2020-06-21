FROM openjdk:14-alpine
EXPOSE 4567

ENV AWS_REGION="us-east-1"

COPY target/lambda-gateway-1.0-SNAPSHOT.jar /srv/app.jar

WORKDIR /srv
CMD java -jar app.jar