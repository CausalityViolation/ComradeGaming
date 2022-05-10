FROM openjdk:17
COPY target/ComradeGaming-1.0.0-SNAPSHOT.jar ComradeGaming-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ComradeGaming-1.0.0-SNAPSHOT.jar"]