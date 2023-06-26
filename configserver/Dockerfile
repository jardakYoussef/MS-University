FROM  openjdk:11
EXPOSE 8889
ADD /target/configserver-0.0.1-SNAPSHOT.jar configserver.jar
ENTRYPOINT ["java", "-jar", "configserver.jar"]
