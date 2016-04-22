package common;

import static common.Utils.readFromConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.jayway.restassured.RestAssured;

public class BaseTestClass {

	public static Logger log = Logger.getLogger(BaseTestClass.class);
	public static String addGoalPath;
	public static String viewGoalsPath;

	@BeforeSuite
	public static void beginTestSuite() {
		log.info("=========== Starting Goals API test suite ===========");
	}

	/***
	 * Load the default URL and API base paths to be used throughout the tests
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@BeforeClass
	public static void setupURL() throws FileNotFoundException, IOException {

		RestAssured.baseURI = readFromConfig("baseURI");
		RestAssured.basePath = readFromConfig("basePath");

		addGoalPath = readFromConfig("addGoalPath");
		viewGoalsPath = readFromConfig("viewGoalsPath");

	}

	@BeforeMethod
	public void beforeMethod(Method methodName) throws Exception {
		log.info("====== launching method " + methodName.getName() + "======");
	}

	@AfterMethod
	public void afterMethod(Method methodName) throws Exception {
		log.info("====== ending method " + methodName.getName() + "======");
	}

	@AfterSuite
	public static void endTestSuite() {
		log.info("=========== Ending Goals API test suite ===========");
	}
}
