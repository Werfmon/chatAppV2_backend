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

ARG MYSQL_ROOT_PASSWORD
ENV MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}

ARG MYSQL_PASSWORD
ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}

ARG MYSQL_DATABASE
ENV MYSQL_DATABASE=${MYSQL_DATABASE}

ARG MYSQL_USER
ENV MYSQL_USER=${MYSQL_USER}

RUN touch ./src/main/resources/secrets.properties
RUN printf "spring.datasource.url=jdbc:mariadb://database/$MYSQL_DATABASE\nspring.datasource.username=$MYSQL_USER\nspring.datasource.password=$MYSQL_PASSWORD" > ./src/main/resources/secrets.properties 

RUN cat ./src/main/resources/application-$ENVIRONMENT.yml > ./src/main/resources/application.yml

RUN ../opt/apache-maven-3.6.3/bin/mvn install -DskipTests

ENTRYPOINT ../opt/apache-maven-3.6.3/bin/mvn spring-boot:run
 
