FROM openjdk:23

COPY ./target/project-testcase-backend-0.0.1-SNAPSHOT.jar project-testcase-backend-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "project-testcase-backend-0.0.1-SNAPSHOT.jar"]
