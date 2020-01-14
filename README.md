# DistributedLogging between multiple Microservice using JMS and ELK stack .Below are the key topics covered in this project.

- Spring Sleuth
- Logback
- RabbitMQ
- Logstash
- ElasticSearch
- Kibana
- Docker
- Docker Compose

## Prerequisite

- Java 8
- Docker (I am using docker for desktop windows version=18.09.2)
- Clone or download project run "mvn clean install" inside CatalogService and CatalogData

## Project Explanation

In this project I have use Logback AmqpAppender to push service log to RabbitMQ Queue. LogStash pipeline configured to pull logs from RabbitMQ queue and push to ElasticSearch. Kibana is for display logs from ElasticSearch

> Flow Digram
![Flow diagram](/img/main1.png)

## Project Setup

Before start we need to compile and create docker images of every module, follow below command sequentially.
**Note: Make sure you have Maven path set at envirnment and docker is up and running *

```
    cd CatalogData
    mvn clean install -Drabbitmq.host=localhost
    docker build -t catalog-data-1 .

    cd CatalogService
    mvn clean install -Drabbitmq.host=localhost
    docker build -t catalog-service-1 .

    cd rabbitmq
    docker build -t rabbitmq-mng .

    cd logstash
    docker build -t logstash .

    cd es
    docker build -t elastic-search .

    cd kibana
    docker build -t kibana .
```