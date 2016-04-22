package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Goal;

/**
 * This class provides some utility methods to run the test cases
 * 
 * @author Deepa
 *
 */
public class Utils {

	public static Logger log = Logger.getLogger(Utils.class);
	private static String CONFIG_PROPERTY_FILE_PATH = "src/test/resources/config.properties";

	/**
	 * Compares the goal objects
	 * 
	 * @param inputGoal
	 * @param outputGoals
	 * @return
	 */
	public static boolean validateGoalAttributes(Goal inputGoal, Goal[] outputGoals) {
		log.debug("Input goal ---" + inputGoal);
		for (Goal aOutputgoal : outputGoals) {
			log.debug("Output goal ---" + aOutputgoal);
			if (aOutputgoal.equals(inputGoal)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Converts a goal object to a Json object and returns it as a string
	 * 
	 * @param goal
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String serializeGoalToJson(Goal goal) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.writeValueAsString(goal).toString();
	}

	public static String getRandomString() {
		return RandomStringUtils.randomAlphabetic(10);
	}

	public static String getRandomString(int range) {
		return RandomStringUtils.randomAlphabetic(range);
	}

	public static String getRandomAlphanumericString() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	public static String getRandomAlphanumericString(int range) {
		return RandomStringUtils.randomAlphanumeric(range);
	}

	public static int getRandomNumber() {
		return (int) (Math.random() * 1000);
	}

	/**
	 * Read the properties from config file
	 * 
	 * @param input
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFromConfig(String input) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(CONFIG_PROPERTY_FILE_PATH));
		String returnVal = properties.getProperty(input);
		return returnVal;
	}

}
