FROM  openjdk:11
EXPOSE 8083
ADD /target/student-service-0.0.1-SNAPSHOT.jar student-service.jar
ENTRYPOINT ["java", "-jar", "student-service.jar"]