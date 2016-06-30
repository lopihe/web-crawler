#Java Web Crawler

##Overview
This is a very simple web crawler implemented in Java 8.

## Requirements
* Java 8
* Maven 3

##Running
In the main project folder, please run the following:
 
> mvn clean package 

> java -jar target/web-crawler-0.0.1-SNAPSHOT.jar <initial url>

##Assumptions
* The sitemap will fit into the JVM heap space, hence no other storage is used 
* Links with non-200 status codes are needed in the sitemap

##Trade-offs, missing features
* Static resources are not identified overall in the application
* Progress meter is not shown
* For the sake of simplicity JUL is used instead of the "standard" SLF4J+Log4j combination
* Throttling is not implemented for websites sensitive to too many requests
* Classification in the sitemap (UrlCatalogue) should be a bit more generic 
* Printing of the sitemap should not be done via System.out but a dedicated printer class.
