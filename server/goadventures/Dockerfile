FROM maven:3.6-jdk-11
VOLUME /app
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
<<<<<<< HEAD
COPY . /app
=======
COPY . /app
>>>>>>> 1ba71f7c717e9f59fefcb471a075236ad143f598
