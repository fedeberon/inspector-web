#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE=web/target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM maven:3.5.2-jdk-8-alpine
WORKDIR .
COPY . /workspace
WORKDIR /workspace
RUN mvn clean install
RUN apk add --update ttf-dejavu
CMD ["mvn", "spring-boot:run","-Drun.arguments='--spring.profiles.active=dev'"]

#Paso 1
# cd web

#Paso 2
# mvn package && java -jar target/web-0.0.1-SNAPSHOT.jar

#Paso 3
# docker build -t fedeberon/jobs_challenge_v1 .