FROM openjdk:17-alpine3.12

RUN apk update

RUN apk add wget \
 && wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz \
 && tar -xvf apache-maven-3.6.3-bin.tar.gz \ 
 && mv apache-maven-3.6.3 /opt/

RUN M2_HOME='/opt/apache-maven-3.6.3' \
 && PATH="$M2_HOME/bin:$PATH" \
 && export PATH 

RUN chmod a+x opt/apache-maven-3.6.3

RUN mkdir /app
WORKDIR /app
COPY . /app

ARG ENVIRONMENT
ENV ENVIRONMENT=${ENVIRONMENT}
RUN echo $ENVIRONMENT

RUN cd /app
RUN ../opt/apache-maven-3.6.3/bin/mvn install -DskipTests 
ENTRYPOINT ../opt/apache-maven-3.6.3/bin/mvn spring-boot:run -Dspring-boot.run.profiles=$ENVIRONMENT
