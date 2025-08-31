FROM maven:3.9-eclipse-temurin-21
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
COPY src/test/resources/testng.xml .

CMD ["sh", "-c", "mvn clean test && mvn allure:report"]