- Requires JDK 6 (1.6)

POSSIBLE BUGS IN IT'S LEARNING's RESTAPI:
- Sequence of XML elements are important. Must be alphabetical, if not returns 401 forbidden.
  This has been taken into consideration in the Java SDK, and will always post alphabetical
  xml element-structure

References:
This SDK uses several 3rd party libraries that a user is required on classpath or in the
web-container. Please investigate the lib folder in the WEB-INF folder of
MyFirstLearningTool for a complete list.