package testcases;

import static common.RestAssuredHelper.getGoals;
import static common.RestAssuredHelper.postAGoal;
import static common.Utils.getRandomNumber;
import static common.Utils.getRandomString;
import static common.Utils.validateGoalAttributes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.BaseTestClass;
import common.StatusCodes;
import domain.Goal;

/***
 * This class comprises of test cases primarily focusing on Happy path
 * scenarios.
 * 
 * @author Deepa
 *
 */
public class DataValidationTests extends BaseTestClass {

	/**
	 * This counter property will keep track of number of goals that has been
	 * sent via the POST request. This counter is later validated in one of the
	 * test case to verify that no extra goals are added in the backend other
	 * than the ones that are sent in the post request.
	 */
	private int counter = 0;

	@Test(groups = "DataValidation")
	public void addAGoalAndValidateTheDataLoad() throws FileNotFoundException, IOException {

		// Test data
		Goal agoal = new Goal();
		agoal.setName(getRandomString());

		// Add a goal
		int statuscode = postAGoal(agoal);
		Assert.assertEquals(statuscode, StatusCodes.redirect);

		// Get all the goals
		Goal[] allGoals = getGoals();

		// Validate the list of goals returned by the get request. Check if the
		// goal that was added earlier is present in this list and the data for
		// all the properties match
		Assert.assertTrue(validateGoalAttributes(agoal, allGoals));

		// Incrementing the counter to keep track of number of goals that were
		// added so far
		counter++;
	}

	@Test(groups = "DataValidation")
	public void addAGoalWithAllFieldsAndValidateTheDataLoad() throws FileNotFoundException, IOException {

		// Test Data
		Goal agoal = new Goal();
		String random = getRandomString();
		agoal.setName(random);
		agoal.setNotes("Notes of " + random);
		agoal.setWeight(getRandomNumber());
		agoal.setDueDate(Calendar.getInstance().getTime());

		// Add a goal
		int statuscode = postAGoal(agoal);
		Assert.assertEquals(statuscode, StatusCodes.redirect);

		// Get all the goals
		Goal[] allGoals = getGoals();

		// Compare the goal that was added with the goals that are retrieved
		Assert.assertTrue(validateGoalAttributes(agoal, allGoals));

		// Incrementing the counter to keep track of number of goals that were
		// added so far
		counter++;
	}

	@Test(groups = "DataValidation")
	public void addAGoalWithFewFieldsAndValidateTheDataLoad() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();
		agoal.setWeight(getRandomNumber());
		agoal.setDueDate(Calendar.getInstance().getTime());

		// Add a goal
		int statuscode = postAGoal(agoal);
		Assert.assertEquals(statuscode, StatusCodes.redirect);

		// Get all the goals
		Goal[] allGoals = getGoals();

		/// Compare the goal that was added with the goals that are retrieved
		Assert.assertTrue(validateGoalAttributes(agoal, allGoals));

		counter++;
	}

	@Test(groups = "DataValidation")
	public void addAGoalWithNoFieldsAndValidateTheDataLoad() throws FileNotFoundException, IOException {
		// Test data
		Goal agoal = new Goal();

		// Add a goal
		int statuscode = postAGoal(agoal);
		Assert.assertEquals(statuscode, StatusCodes.redirect);

		// Get all the goals
		Goal[] allGoals = getGoals();

		// Compare the goal that was added with the goals that are retrieved
		Assert.assertTrue(validateGoalAttributes(agoal, allGoals));

		counter++;
	}

	/**
	 * The below test method gets the data from a data provider The data
	 * provider will return 10 different goals and this test method will be
	 * executed 10 times by taking the 10 different goals as input
	 *
	 * @param newGoal
	 * @param dummy
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test(dataProvider = "supplyGoals")
	public void addMultipleGoalsInDifferentPostCallsAndValidateTheDataLoad(Goal newGoal, String dummy)
			throws FileNotFoundException, IOException {

		// Add a goal
		int statuscode = postAGoal(newGoal);
		Assert.assertEquals(statuscode, StatusCodes.redirect);

		// Get all the goals
		Goal[] allGoals = getGoals();

		// Compare the goal that was added with the goals that are retrieved
		Assert.assertTrue(validateGoalAttributes(newGoal, allGoals));

		counter++;
	}

	@Test(dependsOnGroups = "DataValidation")
	public void retriveAllTheGoalsAndVerifyIfAllTheIDsareUnique() throws FileNotFoundException, IOException {
		// Get all the goals
		Goal[] allGoals = getGoals();

		Set<String> ids = new HashSet<String>();

		for (Goal goal : allGoals) {
			if (!ids.add(goal.getId())) {
				Assert.fail("The goal service has produced some duplicate ids");
			}
		}

	}

	/**
	 * This test cases retrieves all the goals in the backend and compares the
	 * their total with the number of goals that has been added thus far by this
	 * test class.
	 * 
	 * This test case has been written to validate if no duplicate goals are
	 * added by the Post calls.
	 * 
	 * <Assumption> This test cases has been written based on the assumption
	 * that Jetty will be restarted before running this test class/test suite,
	 * so total number of goals in the backend will be zero before the start of
	 * the test class/test suite.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test(dependsOnGroups = "DataValidation")
	public void retriveAllTheGoalsAndVerifyTheSizeOfResponse() throws FileNotFoundException, IOException {
		// Get all the goals
		Goal[] allGoals = getGoals();

		Assert.assertEquals(allGoals.length, counter);

	}

	@DataProvider
	public Object[][] supplyGoals() {
		Object[][] obj = new Object[10][2];

		for (int i = 0; i < 10; i++) {
			Goal newGoal = new Goal();
			int randomNumber = (int) (Math.random() * 100);
			newGoal.setName("Test" + randomNumber);
			newGoal.setNotes("Test Notes" + randomNumber);
			newGoal.setDueDate(Calendar.getInstance().getTime());
			newGoal.setWeight(i);

			obj[i][0] = newGoal;

		}
		return obj;
	}

}
