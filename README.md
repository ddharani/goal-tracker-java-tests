Project description - 
Automation test suite to test the Goal tracker API

Libraries/Test Framework used - 
RestAssured
Testng
Java 8 functions
Json

Installation required to run tests - 
Maven
Testng

Assumptions - 
Assumptions are noted down in the test class above the method signatures

Steps to Execute - 
Command line - 
Deploy the services and restart jetty
Navigate to the root directory of the test project and type the below
		mvn clean install test -DsuiteXmlFile=testngSuite.xml

From editor-
Deploy the services and restart jetty
Right click on the testng xml and click on run as testng suite

Important Notes - 
Before running the test suite, please deploy the services and restart the jetty. This will give a clean backend which helps in validation the results.
Few of the test cases under in the PayloadFormatTesting.java are failing in the assertions. These are the because of the assumptions and the assertions are written accordingly.

 



