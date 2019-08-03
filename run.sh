#!/bin/bash

~/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic panos

mvn install
sleep 1
mvn exec:java -pl thrift-server &
sleep 1
mvn exec:java -pl thrift-client &
sleep 5
mvn exec:java -pl kafka-consumer &
sleep 10

mv logs/logs.txt thrift-client/src/main/resources/

sleep 10
mv thrift-client/src/main/resources/logs.txt logs/