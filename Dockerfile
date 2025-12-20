FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

ENV MAVEN_OPTS="-Xms256m -Xmx1g"

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

CMD ["sh", "-c", "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -DforkCount=1 -DreuseForks=true && mvn allure:report"]
