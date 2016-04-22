package common;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import static common.Utils.serializeGoalToJson;

import java.io.FileNotFoundException;
import java.io.IOException;

import static common.Utils.*;

import domain.Goal;

/**
 * This is a helper class to perform post and get calls to the API service
 * 
 * @author Deepa
 *
 */

public class RestAssuredHelper {

	/**
	 * Converts a goal pojo to json string, Sends a post request to the service
	 * with the json string as the request body
	 * 
	 * @param agoal
	 * @return int
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public static int postAGoal(Goal agoal) throws FileNotFoundException, IOException {
		String jsonAsString = serializeGoalToJson(agoal);
		int status = given().contentType(ContentType.JSON).body(jsonAsString).when().post(readFromConfig("addGoalPath"))
				.getStatusCode();
		return status;
	}

	/**
	 * Sends a post request to the service with the input json string as the
	 * request body
	 * 
	 * @param agoal
	 * @return int
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int postAGoal(String jsonAsString) throws FileNotFoundException, IOException {
		int status = given().contentType(ContentType.JSON).body(jsonAsString).when().post(readFromConfig("addGoalPath"))
				.getStatusCode();
		return status;
	}

	/**
	 * Sends a get request to the service, reads the response and generates an
	 * array of goals
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public static Goal[] getGoals() throws FileNotFoundException, IOException {
		Response response = when().get(readFromConfig("viewGoalsPath")).then().extract().response();
		Goal[] outputGoalsList = response.getBody().as(Goal[].class);
		return outputGoalsList;
	}
}
