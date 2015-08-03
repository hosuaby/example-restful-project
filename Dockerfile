FROM java:8
MAINTAINER Hosuaby <alexey_klenin@hotmail.fr>

# Set environment variables
ENV PORT=80 \
    SPRING_DATA_MONGODB_URI=mongodb://db:27017/db_teapots

# Exposes port of web application
EXPOSE $PORT

# Copy JAR file to container
COPY target/*.jar /

# Launch java application
CMD java $JAVA_OPTS -Dserver.port=$PORT -jar /*.jar