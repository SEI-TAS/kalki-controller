# First stage: build.
ARG KALKI_DB_VER="latest"
FROM kalki/kalki-db-env:$KALKI_DB_VER AS build_env
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
ARG SKIP_TESTS=""
RUN gradle build $SKIP_TESTS --no-daemon

# Second stage: actual run environment.
FROM openjdk:8-jre-alpine

# Install ovs-tools
RUN apk --no-cache add bash iproute2

RUN mkdir -p /logs/

ARG PROJECT_NAME=kalki-main-controller
ARG PROJECT_VERSION=1.7.0
ARG DIST_NAME=$PROJECT_NAME-$PROJECT_VERSION

COPY --from=build_env /home/gradle/src/build/distributions/$DIST_NAME.tar /
RUN tar -xvf $DIST_NAME.tar && \
    rm $DIST_NAME.tar && \
    mv /$DIST_NAME /$PROJECT_NAME

COPY config.json /$PROJECT_NAME/

WORKDIR /$PROJECT_NAME
ENTRYPOINT ["bash", "bin/kalki-main-controller"]
