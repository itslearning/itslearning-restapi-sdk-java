- Requires JDK 6 (1.6)

POSSIBLE BUGS IN IT'S LEARNING's RESTAPI:
- Sequence of XML elements are important. Must be alphabetical, if not returns 401 forbidden.
  This has been taken into consideration in the Java SDK, and will always post alphabetical
  xml element-structure

References:
This SDK uses several 3rd party libraries that a user is required on classpath or in the
web-container. Please investigate the lib folder in the WEB-INF folder of
MyFirstLearningTool for a complete list.
Alternatively, you could setup a custom pom.xml file for use with Maven.

RestApi.Sdk.Common uses the following:
* j2ee.jar
* org.springframework.web-3.0.0.M3 incl dependencies

RestApi.Sdk.LearningToolApp uses the following:
* commons-codec-1.4 incl. dependencies
* commons-logging-1.1.1 incl dependencies
* dom4j-1.6.1 incl dependencies
* org.apache.commons.httpclient_3.1 incl dependencies
* j2ee.jar