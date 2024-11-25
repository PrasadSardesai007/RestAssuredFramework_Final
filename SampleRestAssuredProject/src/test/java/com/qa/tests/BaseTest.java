package com.qa.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.internal.ITestInvoker;

import com.qa.extentreport.ExtentReportManager;
import com.qa.jsonparser.JsonPathParser;
import com.qa.requestspecifications.ApiUtils;

import io.restassured.response.Response;

/**
 * BaseTest class that serves as a foundation for all API test classes. It
 * initializes the API utilities, sets up reporting, and provides common methods
 * for logging and validating API requests and responses.
 */
public class BaseTest {

	// Protected variables for API utilities and base URI
	protected ApiUtils apiUtils;
	protected String baseUri;

	/**
	 * Constructor to initialize BaseTest with the provided base URI.
	 *
	 * @param baseUri The base URI for the API being tested.
	 */
	public BaseTest(String baseUri) {
		this.baseUri = baseUri;
	}

	/**
	 * Method to set up the test environment before any test methods are executed.
	 * Initializes the extent reporting and API utilities.
	 */
	@BeforeClass
	public void setUp() {
		ExtentReportManager.initReports(); // Initialize reporting
		apiUtils = ApiUtils.init().setBaseUri(baseUri); // Initialize API utils with base URI
	}

	/**
	 * Logs the request and response details for GET requests.
	 *
	 * @param path        The API endpoint path.
	 * @param queryParams The query parameters for the request.
	 * @param response    The response received from the API.
	 */
	protected void logRequestAndResponse(String path, Map<String, String> queryParams, Response response) {
		ExtentReportManager.logRequest(apiUtils.getBaseUri(baseUri), path, queryParams);
		ExtentReportManager.logInfo("Response Status Code: " + response.statusCode());
		ExtentReportManager.logResponse(response);
	}

	/**
	 * Logs the request and response details for POST (or other) requests.
	 *
	 * @param path     The API endpoint path.
	 * @param request  The request body as a JSON string.
	 * @param response The response received from the API.
	 */
	protected void logRequestAndResponse(String path, String request, Response response) {
		ExtentReportManager.logRequest(apiUtils.getBaseUri(baseUri), path, JsonPathParser.prettyPrint(request));
		ExtentReportManager.logInfo("Response Status Code: " + response.statusCode());
		ExtentReportManager.logResponse(response);
	}

	/**
	 * Validates the API response and logs success message.
	 *
	 * @param response           The response received from the API.
	 * @param expectedStatusCode The expected HTTP status code.
	 * @param successMessage     The message to log if the validation is successful.
	 */
	protected void validateResponse(Response response, int expectedStatusCode, String successMessage) {
		try {
			Assert.assertNotNull(response, "Response should not be null."); // Ensure response is not null
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch."); // Check status
																										// code
			ExtentReportManager.logPass(successMessage); // Log success message

		} catch (AssertionError e) {
			ExtentReportManager.logFail("Validation Failed: " + e.getMessage()); // Log failure message
			throw e; // Rethrow the exception to indicate a validation failure
		}
	}

	/**
	 * Asserts that two values are equal and logs the result.
	 *
	 * @param actual         The actual value to be checked.
	 * @param expected       The expected value to compare against.
	 * @param failureMessage Message to be displayed in case of an assertion
	 *                       failure.
	 * @param passMsg        Message to be logged when the assertion passes.
	 * @param failMsg        Message to be logged when the assertion fails.
	 * @throws AssertionError if the actual value does not equal the expected value.
	 */
	protected void assertEquals(String actual, String expected, String failureMessage, String passMsg, String failMsg) {
		try {
			// Perform the assertion, comparing actual and expected values.
			Assert.assertEquals(actual, expected, failureMessage);

			// Log a success message if the assertion passes.
			ExtentReportManager.logPass("Test passed: " + passMsg);
		} catch (AssertionError e) {
			// Log a failure message if the assertion fails, including the exception
			// message.
			ExtentReportManager.logFail("Test Failed: " + failMsg + " " + e.getMessage()); // Added space for formatting
			throw e; // Rethrow the exception to indicate a test failure.
		}
	}

	/**
	 * Method to tear down the test environment after all test methods have been
	 * executed. Ends the extent reporting session.
	 */

	@AfterClass
	public void tearDown() {

		ExtentReportManager.endTest(); // End the reporting session

	}
}