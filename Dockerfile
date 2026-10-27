FROM eclipse-temurin:17-jre
RUN mkdir /project
COPY target/jenkines_demo_project-0.0.1-SNAPSHOT.jar /project/app.jar
EXPOSE 9091
CMD ["java", "-jar", "/project/app.jar"]