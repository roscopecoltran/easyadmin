FROM alpine:3.6

# docker build -t spring-boot-maven3 --no-cache .
# docker run --rm -ti -p 8080:8080 -v `pwd`:/app spring-boot-maven3

ENV MAVEN_VERSION=3.5.0
ENV MAVEN_HOME /usr/share/maven
ENV LANG C.UTF-8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin
ENV JAVA_VERSION 8u131
ENV JAVA_ALPINE_VERSION 8.131.11-r2


# add a simple script that can auto-detect the appropriate JAVA_HOME value
# based on whether the JDK or only the JRE is installed
RUN { \
		echo '#!/bin/sh'; \
		echo 'set -e'; \
		echo; \
		echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; \
	} > /usr/local/bin/docker-java-home \
	&& chmod +x /usr/local/bin/docker-java-home \
  && set -x \
  && apk add --no-cache --update bash curl nano openjdk8="$JAVA_ALPINE_VERSION" \
  && [ "$JAVA_HOME" = "$(docker-java-home)" ] \
  && mkdir -p /usr/local/maven /opt/app \
	&& chown -R 1001:1001 /opt/app \
	&& curl -jksSL -o /tmp/apache-maven.tar.gz https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
	&& gunzip /tmp/apache-maven.tar.gz \
	&& tar -C /usr/local -xf /tmp/apache-maven.tar \
	&& ln -s /usr/local/apache-maven-$MAVEN_VERSION/bin/mvn /usr/bin/mvn \
	&& apk del curl \
	&& rm -rf /tmp/* /var/cache/apk/*

COPY ./docker /usr/local/docker
ADD . /app

# USER 1001

EXPOSE 8080

VOLUME ["/data"]
# VOLUME /root/.m2
WORKDIR /app

CMD ["/bin/bash"]
# CMD ["/usr/local/docker/usage"]
# https://github.com/kongchen/swagger-maven-example/blob/master/pom.xml