#!/bin/zsh

# did you change the version number?
sbt clean
sbt assembly
sbt docker:publishLocal
docker image tag iotkafkaanemometer:latest intel-server-03:5000/iotkafkaanemometer
docker image push intel-server-03:5000/iotkafkaanemometer

# Server side:
# kubectl apply -f /home/appuser/deployments/anemometerKafka.yaml
# If needed:
# kubectl delete deployment iot-bme680-kafka-reader
# For troubleshooting
# kubectl exec --stdin --tty iot-bme680-kafka-reader -- /bin/bash
