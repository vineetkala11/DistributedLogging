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

In this project I have use Logback AmqpAppender to push service log over RabbitMQ Queue. LogStash pipeline configured to pull logs from RabbitMQ queue and push to ElasticSearch. Kibana is for display logs from ElasticSearch

> Flow Digram
![Flow diagram](/img/main2.png)

## Project Setup

Before start we need to compile and create docker images of every module, follow below command sequentially.

**Note: Make sure you have Maven path set at envirnment and docker is up and running*

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

use below command to verify all six docker images we just created - 

```
    docker images
```

we are now done with the initial setup.

## RabbitMQ Exchange and Queue setup

Now we need to setup RabbitMQ Exchange and Queue, where our services will push there logs. Follow below commands

```
    cd docker-compose
    docker-compose up
```

"docker-compose up" starts all six containers, on console logs you can see catalog-data-1 and catalog-service-1 unable to make connection with RabbitMQ Exchange "log-exchange", its becuase we didn't create any Exchage till now.

To setup new exchange we take help of RabbitMQ Managment UI, keep docker-compose window up and running and open below url in your browser. To login into RabbitMQ Managment UI use user as "guest" and password as "guest" (its a default user/password) -  

*http://localhost:15672/*

Once you successful login into RabbitMQ Managment UI, you need to create new Exchange and Queue, and the bind Queue with the Exchange. I found good video over youtube for setting up RabbitMQ Exchange and queue, follow these instructions as given in below video.


[![RabbitMQ - Creating Queue, Exchange and Binding and Publishing Message](/img/rabbitmq-setup.png)](https://www.youtube.com/watch?v=OP2MjpYY5Oc "RabbitMQ - Creating Queue, Exchange and Binding and Publishing Message")

**Note : Replace name of Exchange and queue from given video - "TestExchange" with "log-exchange" and "TestQueue" with "center-log-queue"*

Once you are done with RabbitMQ Exchange and Queue setup, stop docker-compose (Ctrl c) and restart it again. Verify, this time catalog-data-1 and catalog-service-1 successfully connected with RabbitMQ Exchange.

## Check logs in Kibana

Now time to check logs in kibana, kibana is already up and running at port "5601". Open below url on browser to view Kibana UI.

*http://localhost:5601*

Below screen you will see -

![Kibana Home](/img/kibana-1.png)


To check incomming logs from RabitMQ through logstach, click on Discover tab (top left), as shown in below image -


![Kibana Home](/img/kibana-2.png)

You can eaisly indentify your application startup logs in kibana dashboard.

- Now I hit below rest endpoint, exposed in catalog-service-1 (catalog-service-1 internally access catalog-data-1 through rest api) 

*http://localhost:8100/service/fetchCatalog*

