FROM openjdk:8u151-jdk-alpine3.7
WORKDIR '/app'
COPY target/palindrome-message-checker-1.0-SNAPSHOT-shaded.jar ./palindrome-message-checker-1.0-SNAPSHOT-shaded.jar
CMD ["/java", "-jar", "palindrome-message-checker-1.0-SNAPSHOT-shaded.jar"]
