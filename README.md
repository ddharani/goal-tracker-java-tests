<B>Project description</B> - Automation test suite to test the Goal tracker API

<B>Libraries/Test Framework used</B> -  RestAssured, Testng, Java 8 functions, Json libraries

<B>Tools required to run the tests</B> - Maven, Testng

<B>Assumptions</B> - Assumptions are noted down in the respective test classes 

<B>Steps to Execute </B> - 

1. Command line - Deploy the services and restart jetty. Navigate to the root directory of the test project and type the below
		
	mvn clean install test -DsuiteXmlFile=testngSuite.xml

2. From editor - Deploy the services and restart jetty. Right click on the testng xml and click on run as testng suite.

<B>Important Notes - </B>

Before running the test suite, please deploy the services and restart the jetty. This will give a clean backend which helps in validation the results.
Few of the test cases under in the PayloadFormatTesting.java are failing in the assertions. These are the because of the assumptions and the assertions are written accordingly.
