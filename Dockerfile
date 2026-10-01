FROM tomcat:10.1-jdk21-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/automated-grocery-inventory-dashboard-0.0.1-SNAPSHOT.war \
     /usr/local/tomcat/webapps/automated-grocery-inventory-dashboard.war

EXPOSE 8080

CMD ["catalina.sh", "run"]