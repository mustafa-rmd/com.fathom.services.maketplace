FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS=""
ENV OTHER_JAVA_OPTS=""
# For remote debugging add: -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5050,server=y,suspend=n
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS $OTHER_JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
