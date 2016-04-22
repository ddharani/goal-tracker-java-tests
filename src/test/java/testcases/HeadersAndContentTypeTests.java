package testcases;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static common.Utils.getRandomString;
import static common.Utils.serializeGoalToJson;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.http.ContentType;

import common.BaseTestClass;
import common.StatusCodes;
import domain.Goal;

/**
 * This test class has most of negative scenarios covered.
 * 
 * @author Deepa
 *
 */
public class HeadersAndContentTypeTests extends BaseTestClass {

	@Test
	public void sendPostRequestWithInvalidContentType() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomString());
		String jsonAsString = serializeGoalToJson(agoal);

		// add a goal
		int statuscode = given().contentType(ContentType.XML).body(jsonAsString).when().post(addGoalPath)
				.getStatusCode();

		Assert.assertEquals(statuscode, StatusCodes.unsupportedMediaType);

	}

	@Test
	public void sendGetRequestAndVerifyContentType() throws FileNotFoundException, IOException {

		// add a goal and validate the contentType of the response
		given().expect().contentType(ContentType.JSON).when().get(viewGoalsPath);
	}

	@Test
	public void sendPostRequestToAWrongPath() throws FileNotFoundException, IOException {

		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomString());
		String jsonAsString = serializeGoalToJson(agoal);

		// add a goal
		int statuscode = given().contentType(ContentType.JSON).body(jsonAsString).when().post("/goals").getStatusCode();

		Assert.assertEquals(statuscode, StatusCodes.methodNotAllowed);
	}

	@Test
	public void sendGetRequestToaWrongPath() throws FileNotFoundException, IOException {

		// send a get request to a wrong method
		int statuscode = get("/goal").getStatusCode();

		Assert.assertEquals(statuscode, StatusCodes.methodNotAllowed);
	}

	@Test
	public void sendGetRequestToNonExistingPath() {

		// send a get request to a wrong method
		int statuscode = get("/allgoals").getStatusCode();
		Assert.assertEquals(statuscode, StatusCodes.notFound);
	}

	@Test
	public void sendPostRequestWithAHeader() throws JsonProcessingException {

		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomString());
		String jsonAsString = serializeGoalToJson(agoal);

		// add a goal by parsing in a header value
		int statuscode = given().header("authorname", "test author").contentType(ContentType.JSON).body(jsonAsString)
				.when().post("goal").getStatusCode();

		Assert.assertEquals(statuscode, StatusCodes.redirect);

	}
}
