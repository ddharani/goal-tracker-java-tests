package testcases;

import static com.jayway.restassured.RestAssured.when;
import static common.RestAssuredHelper.getGoals;
import static common.RestAssuredHelper.postAGoal;
import static common.Utils.getRandomString;
import static common.Utils.readFromConfig;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.BaseTestClass;
import domain.Goal;

public class LoadAndPeformanceTests extends BaseTestClass {

	private int allGoalsLengthBeforePost = 0;

	/**
	 * The method under the before class annotation will calculate the number of
	 * goals present in the backend before running the stress test
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@BeforeClass
	public void getAllGoalsBeforePost() throws FileNotFoundException, IOException {
		this.allGoalsLengthBeforePost = getGoals().length;
		System.out.println("All goals before post : " + allGoalsLengthBeforePost);
	}

	/**
	 * This method does a post call in a multithreaded environment of
	 * threadpoolsize of 10 and with a invocation count of 1000 and 60 second
	 * timeout check
	 * 
	 * The above is a benchmark that has been assumed and all the methods are
	 * expected to run before the timeout
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test(threadPoolSize = 10, invocationCount = 1000, timeOut = 60 * 1000)
	public void postRequestLoadAndPerfTest() throws FileNotFoundException, IOException {
		try {
			// Test data
			Goal agoal = new Goal();
			agoal.setName(getRandomString());
			postAGoal(agoal);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will compare the total number of goals is the backend before
	 * and after the post calls.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test(dependsOnMethods = "postRequestLoadAndPerfTest")
	public void getAllGoalsAndVerifyIfAllTheGoalsFromThePOSTareLoaded() throws FileNotFoundException, IOException {
		int allGoalsLengthAfterPost = getGoals().length;
		Assert.assertEquals(allGoalsLengthAfterPost, allGoalsLengthBeforePost + 1000);
	}

	/**
	 * This method validates the response time of the get request
	 * 
	 * Assumption - 100 Milliseconds has been assumed as the benchmark. The
	 * service can take up to 100 milliseconds for response of the get call.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test(dependsOnMethods = "postRequestLoadAndPerfTest")
	public void validateTheResponseTimeOfGetRequest() throws FileNotFoundException, IOException {

		long responseTime = when().get(readFromConfig("viewGoalsPath")).thenReturn().getTime();
		Assert.assertTrue(responseTime < 100);

	}
}
