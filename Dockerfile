FROM openjdk:17
ARG JAR_FILE=build/libs/coupon-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV PINPOINT_VERSION=2.5.3
ENV AGENT_ID=app-in-docker
ENV APP_NAME=AYU-coupon-serivce

CMD java -javaagent:/pinpoint-agent/pinpoint-bootstrap-${PINPOINT_VERSION}.jar \
    -Dpinpoint.agentId=${AGENT_ID} \
    -Dpinpoint.applicationName=${APP_NAME} \
    -Dpinpoint.profiler.profiles.active=release \
    -jar ./app.jar
