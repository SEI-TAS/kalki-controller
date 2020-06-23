# First stage: build.
FROM kalki/kalki-db-env AS build_env
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Second stage: actual run environment.
FROM openjdk:8-jre-alpine

# Install ovs-tools
RUN apk --no-cache add bash iproute2

ARG PROJECT_NAME=kalki-main-controller
ARG PROJECT_VERSION=1.6.0
ARG DIST_NAME=$PROJECT_NAME-$PROJECT_VERSION

COPY --from=build_env /home/gradle/src/build/distributions/$DIST_NAME.tar /
RUN tar -xvf $DIST_NAME.tar && \
    rm $DIST_NAME.tar && \
    mv /$DIST_NAME /$PROJECT_NAME

COPY config.json /$PROJECT_NAME/

WORKDIR /$PROJECT_NAME
ENTRYPOINT ["bash", "bin/kalki-main-controller"]
