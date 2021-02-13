FROM mysql
ENV MYSQL_DATABASE xmeme
ENV MYSQL_USER harikesh
ENV MYSQL_PASSWORD harikesh
ENV MYSQL_ROOT_PASSWORD root
EXPOSE 3306

FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD
# copy the pom and src code to the container
COPY ./backend/XMeme/ /tmp/
WORKDIR /tmp/

# package our application code
RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-alpine

COPY --from=MAVEN_BUILD /tmp/target/XMeme.jar /XMeme.jar
EXPOSE 8081
CMD ["java","-jar","/XMeme.jar"]
