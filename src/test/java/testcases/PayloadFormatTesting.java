package testcases;

import static common.RestAssuredHelper.postAGoal;
import static common.Utils.getRandomAlphanumericString;
import static common.Utils.getRandomNumber;
import static common.Utils.getRandomString;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.BaseTestClass;
import common.StatusCodes;
import domain.Goal;

/**
 * This class comprises of test cases that tests all fields in the goal object
 * 
 * @author Deepa
 *
 */
public class PayloadFormatTesting extends BaseTestClass {

	@Test
	public void addAGoalWithNameContainingAlphanumeric() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomAlphanumericString());

		// Add a goal
		int statuscode = postAGoal(agoal);
		Assert.assertEquals(statuscode, StatusCodes.redirect);
	}

	@Test
	public void addAGoalWithNameContainingOnlyNumeric() throws FileNotFoundException, IOException {
		// Test data
		JSONObject json = new JSONObject();
		json.put("name", Integer.MAX_VALUE);

		// Add a goal
		int statuscode = postAGoal(json.toString());

		// Assumption - The service should throw an error and should not
		// redirect to all goals
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithNameContainingNullvalues() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setName(null);

		// Add a goal
		int statuscode = postAGoal(agoal);

		// Assumption - The service should throw an error and should not
		// redirect to all goals
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithNameContainingEmptyValue() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setName("");

		// Add a goal
		int statuscode = postAGoal(agoal);

		// Assumption - The service not throw an error when the name has empty
		// values
		Assert.assertEquals(statuscode, StatusCodes.redirect);
	}

	@Test
	public void addAGoalWithNameContainingLongStringLength() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		String testString = getRandomAlphanumericString(999999);
		agoal.setName(testString);

		// Add a goal
		int statuscode = postAGoal(agoal);

		// Assumption - The service should not be able to handle larger strings
		// beyond a certain threshold
		Assert.assertEquals(statuscode, StatusCodes.servererror);
	}

	@Test
	public void addAGoalWithDateContainingInvalidValue() throws FileNotFoundException, IOException {
		// Test data
		JSONObject json = new JSONObject();
		json.put("name", Integer.MAX_VALUE);
		json.put("date", Integer.MIN_VALUE);

		// Add a goal
		int statuscode = postAGoal(json.toString());

		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithWeightContainingNegativeValue() throws FileNotFoundException, IOException {
		// Test data
		JSONObject json = new JSONObject();
		json.put("name", getRandomAlphanumericString());
		json.put("weight", Integer.MIN_VALUE);

		// Add a goal
		int statuscode = postAGoal(json.toString());

		// Assumption - The service should not throw an error when the negative
		// integer value has been assigned to the weight property in goal object
		Assert.assertEquals(statuscode, StatusCodes.redirect);
	}

	@Test
	public void addAGoalWithWegihtContainingDoubleValue() throws FileNotFoundException, IOException {
		// Test data
		JSONObject json = new JSONObject();
		json.put("name", getRandomAlphanumericString());
		json.put("weight", 3.455);

		// Add a goal
		int statuscode = postAGoal(json.toString());

		// Assumption - The service should throw an error when double value has
		// been assigned to the weight property in goal object
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithWegihtContainingStringValue() throws FileNotFoundException, IOException {
		// Test data
		JSONObject json = new JSONObject();
		json.put("name", getRandomAlphanumericString());
		json.put("weight", "testWeight");

		// Add a goal
		int statuscode = postAGoal(json.toString());

		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithNotesContainingIntegerValue() throws FileNotFoundException, IOException {
		// Test data
		JSONObject json = new JSONObject();
		json.put("name", getRandomAlphanumericString());
		json.put("notes", getRandomNumber());

		// Add a goal
		int statuscode = postAGoal(json.toString());

		// Assumption - The service should throw an error when numeric value has
		// been assigned to the notes property in goal object
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithNotesContainingNullvalues() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomString());
		agoal.setNotes(null);

		// Add a goal
		int statuscode = postAGoal(agoal);

		// Assumption - The service should throw an error and should not
		// redirect to all goals
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithNotesContainingEmptyValue() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomString());
		agoal.setNotes("");

		// Add a goal
		int statuscode = postAGoal(agoal);

		// Assumption - The service should not throw an error when notes
		// property has been assigned with empty value
		Assert.assertEquals(statuscode, StatusCodes.redirect);
	}

	@Test
	public void addAGoalWithNotesContainingLongStringLength() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomString());
		String testString = getRandomAlphanumericString(999999);
		agoal.setNotes(testString);

		// Add a goal
		int statuscode = postAGoal(agoal);

		// Assumption - The service should not be able to handle larger strings
		// beyond a certain threshold
		Assert.assertEquals(statuscode, StatusCodes.servererror);
	}

	@Test
	public void addAGoalWithNullInputAndValidateTheDataLoad() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = null;

		// Add a goal
		int statuscode = postAGoal(agoal);

		Assert.assertEquals(statuscode, StatusCodes.servererror);
	}

	@Test
	public void addMoreThanOneGoalinAPostRequest() throws FileNotFoundException, IOException {

		// Setting up to Json objects and concatenate them to send as the POST
		// request body
		JSONObject json1 = new JSONObject();
		json1.put("name", "originalUser");
		json1.put("notes", "originalUser notes");

		JSONObject json2 = new JSONObject();
		json2.put("name", "dupUser");
		json2.put("notes", "dupUser notes");

		String jsonString = json1.toString().concat(json2.toString());
		System.out.println(jsonString);

		// Add a goal
		int statuscode = postAGoal(jsonString);

		// Assumption - The service should validate if the post call sends only
		// one json object
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithDuplicateFields() throws FileNotFoundException, IOException {

		JSONObject json = new JSONObject();
		json.put("name", "originalUser1");
		json.put("name", "dupeUser1");
		json.put("name", "dupeUser2");
		json.put("notes", "originalUser1 notes");
		json.put("notes", "dupeUser1 notes");
		json.put("notes", "dupeUser2 notes");

		// Add a goal
		int statuscode = postAGoal(json.toString());

		// Assumption - The service should validate if the post call sends the
		// json object without duplicate fields
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

	@Test
	public void addAGoalWithAInvalidFieldAndVerifyIfThePostRequestFailed() throws FileNotFoundException, IOException {
		// Test data
		String jsonString = "{\"name\":\"orignalUser\",\"notes\":\"orignalUser notes\", \"height\":\"6 inches\"}";

		// Add a goal
		int statuscode = postAGoal(jsonString);

		// Assumption - The service should validate if the post call sends the
		// json object that has only valid fields
		Assert.assertEquals(statuscode, StatusCodes.badrequest);
	}

}
