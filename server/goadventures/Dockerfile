FROM maven:3.6-jdk-11
VOLUME /app
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY . /app
