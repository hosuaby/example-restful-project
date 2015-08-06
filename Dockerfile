FROM maven:3-jdk-8
MAINTAINER Hosuaby <alexey_klenin@hotmail.fr>

# Set environment variables
ENV PORT=80 \
    SPRING_DATA_MONGODB_URI=mongodb://db:27017/db_teapots

# Exposes port of web application
EXPOSE $PORT

# Copy sources to container
COPY pom.xml /restful/
COPY src /restful/src

# Change work directory to /restful
WORKDIR /restful

# Build sources with maven
RUN mvn install

# Launch java application
CMD java $JAVA_OPTS -Dserver.port=$PORT -jar target/*.jar