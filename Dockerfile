FROM docker.dxc.com/eprocurement-docker/openjdk:15-alpine
COPY catalogue-admin-service.jar /deployments/catalogue-admin-service.jar
EXPOSE 8080
CMD java -Dspring.profiles.active=$PROFILE -jar /deployments/catalogue-admin-service.jar --DB_HOSTNAME=$DATABASE_SVC --DB_PORT=$DATABASE_PORT --DB_DATABASE=$DATABASE_NAME --DB_USER=$DATABASE_USER --DB_PASSWORD=$DATABASE_PASSWORD --amqphub.amqp10jms.remote-url=amqp://$JMS_SERVER:$JMS_SERVER_PORT --MASTER-SERVICE-URL=$MASTER-SERVICE-URL
