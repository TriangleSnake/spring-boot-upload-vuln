FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

RUN apt-get update && apt-get install -y socat

COPY demo /app

CMD ["mvn","spring-boot:run"]