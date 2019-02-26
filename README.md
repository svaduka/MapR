# MapR
This project is used for MapR Team members

Following are the list of items need to learn/develop by every team member of MapR-Team

## Knowledge Required

1) Java/Scala
2) Maven
3) OJAI API
4) MapR-Stream
5) MapRDB
6) JSON
7) SCP
8) ssh
9) Deployment
10) Spring-Boot
11) Apache Hadoop
12) Apache Spark
13) Kafka

## Environment Setup

1) Download Virtual Box using below link
  https://www.virtualbox.org/wiki/Downloads
2) Download the latest (6.1.0-Mapr) Sandbox Hadoop Virtual box OVA file from the below link, it is around 4.9 GB takes time
https://mapr.com/products/mapr-sandbox-hadoop/download/
3) Setup the MapR environment. FYI : UserName: mapr Password: mapr
4) Download the scala eclipse ide from the below link: http://scala-ide.org/download/sdk.html
5) Setup the eclipse.

## UseCases

1) Execute end-to-end mapr-streams producer and consumer examples provided in the below github location https://github.com/svaduka/mapr-streams-sample-programs.git
using below steps:
    a) Clone the github 
    b) build the jar
    c) deploy the jar in the mapr VM
    d) execute producer and consumer. Copy the producer output and consumer output and create a word document
2) Execute end-to-end mapr-db with OJAI API examples provided in the below github location https://github.com/svaduka/ojai-examples.git
using below steps:
    a) Clone the github 
    b) build the jar
    c) deploy the jar in the mapr VM
    d) Write data to MapR-DB
    
3) Integrate the usecase-1 and usecase-2 and produce JSON data and write to MapR-Streams and consumer the JSON data and write data to MapR-DB using maven project.For reference you can use the current github project
4) Execute a spring-boot example as specified in the below github location: https://github.com/svaduka/spring-boot-rest.git
5) Integrate usecase-4 and usecase-3, do the following usecases:
    a) Create a rest sevice using spring-boot which accepts an id as below
          example: curl "http://localhost:8080/test/service?id=12121
    b) The sprint boot controller will accept the request and get the id(id=12121)
    c) The controller will use MapR-DB service Utility call the table(of your choice) and get the value of id=12121.
    
For your reference you can use the current project to do all test cases.
