# Alexa Bus Status

This repo is a bare-bones Java/Maven project for building an Amazon AWS Lambda endpoing for an Alexa skill.

## Prerequisites

To build this project, you need the following installed on your workstation:

* Java 8+
* Apache Maven 3.5.2+

## Configuring

In order to use this lambda code with your custom Alexa skill, you must take the Alexa skill id and paste it inside `BusStatusSpeechletRequestStreamHandler.java`

## Building

Run the following command to build the binary package, which will generate target/busstatus-1.0-jar-with-dependencies.jar

`mvn assembly:assembly -DdescriptorId=jar-with-dependencies package`

## Uploading to Amazon

Once you build the target, you can upload the jar file above to Amazon by going to https://console.aws.amazon.com/lambda/home, selecting your lambda, and uploading the jar file.
