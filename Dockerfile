FROM openjdk:17-jdk
WORKDIR /app
COPY target/atomize-0.0.1-SNAPSHOT.jar /app/atomize.jar
EXPOSE 8081
CMD ["java" ,"-jar","atomize.jar"]