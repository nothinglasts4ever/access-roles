FROM openjdk:12-oracle
VOLUME ./data/access-roles-app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]