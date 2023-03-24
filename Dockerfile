FROM openjdk:8
EXPOSE 8484
ADD target/webclientoperators.jar webclientoperators.jar
ENTRYPOINT ["java","-jar","/webclientoperators.jar"]