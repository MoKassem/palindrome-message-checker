FROM openjdk:8u151-jdk-alpine3.7
COPY target/palindrome-message-checker-1.0-SNAPSHOT-shaded.jar /app/palindrome-message-checker-1.0-SNAPSHOT-shaded.jar
WORKDIR '/app'
CMD ["java", "-jar", "palindrome-message-checker-1.0-SNAPSHOT-shaded.jar"]
