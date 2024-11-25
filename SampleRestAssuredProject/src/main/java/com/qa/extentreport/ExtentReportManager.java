package com.qa.extentreport;

import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.response.Response;

/**
 * ExtentReportManager is a utility class that manages the setup and logging for
 * Extent Reports. It provides methods to initialize the report, start tests,
 * and log various types of messages, including requests and responses.
 */
public class ExtentReportManager {
	private static ExtentReports extent; // Instance of ExtentReports to manage the report
	private static boolean flag=false;
	private static ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<ExtentTest>(); // Thread-safe storage for
																							// ExtentTest

	/**
	 * Initializes the Extent Reports instance with an HTML reporter. The report
	 * will be generated in the current directory with the name "extentReport.html".
	 */
	public static void initReports() {
		if(extent==null) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extentReport.html");
		extent = new ExtentReports();// Create an instance of ExtentReports
		htmlReporter.config().setTheme(Theme.DARK);
		extent.attachReporter(htmlReporter);
		}// Attach the HTML reporter to the report
	}

	/**
	 * Starts a new test in the Extent Report.
	 * 
	 * @param testName The name of the test to be logged in the report.
	 */
	public static void startTest(String testName) {
		ExtentTest test = extent.createTest(testName); // Create a test instance
		testThreadLocal.set(test); // Store the test instance in ThreadLocal
	}

	/**
	 * Logs an informational message to the current test in the report.
	 * 
	 * @param message The message to log.
	 */
	public static void logInfo(String message) {
		testThreadLocal.get().info(message); // Log the informational message
	}

	/**
	 * Logs a highlighted informational message to the current test in the report.
	 * 
	 * @param message The message to log.
	 */
	public static void logHighlightedInfo(String message) {
		// Log with HTML formatting for highlights
		testThreadLocal.get().info("<b style='color: black;'>" + message + "</b>");
	}

	/**
	 * Logs the details of an API request, including the base URI, base path, and
	 * query parameters.
	 * 
	 * @param baseUri     The base URI of the API.
	 * @param basePath    The base path of the API endpoint.
	 * @param queryParams The query parameters sent with the request.
	 */
	public static void logRequest(String baseUri, String basePath, Map<String, String> queryParams) {
		StringBuilder requestLog = new StringBuilder();
		requestLog.append("<b>Request Details:</b><br>");
		requestLog.append("Base URI: <b>").append(baseUri).append("</b><br>");
		requestLog.append("Base Path: <b>").append(basePath).append("</b><br>");
		requestLog.append("<b>Query Parameters:</b><br>");
		requestLog.append("<table border='1'><tr><th>Parameter</th><th>Value</th></tr>");

		// Loop through query parameters and add them to the table
		for (Map.Entry<String, String> entry : queryParams.entrySet()) {
			requestLog.append("<tr><td>").append(entry.getKey()).append("</td><td>").append(entry.getValue())
					.append("</td></tr>");
		}
		requestLog.append("</table>");

		testThreadLocal.get().info(requestLog.toString()); // Log the request details
	}

	/**
	 * Logs the details of an API request, including the base URI, base path, and
	 * request payload.
	 * 
	 * @param baseUri        The base URI of the API.
	 * @param basePath       The base path of the API endpoint.
	 * @param requestPayload The request payload (e.g., JSON, XML) sent with the
	 *                       request.
	 */
	public static void logRequest(String baseUri, String basePath, String requestPayload) {
		StringBuilder requestLog = new StringBuilder();
		requestLog.append("<b>Request Details:</b><br>");
		requestLog.append("Base URI: <b>").append(baseUri).append("</b><br>");
		requestLog.append("Base Path: <b>").append(basePath).append("</b><br>");
		requestLog.append("<b>Request Payload:</b><br>");

		// Log the request payload
		if (requestPayload != null && !requestPayload.isEmpty()) {
			requestLog.append("<pre>").append(requestPayload).append("</pre>");
		} else {
			requestLog.append("<i>No Payload Provided</i>");
		}

		testThreadLocal.get().info(requestLog.toString()); // Log the request details
	}

	/**
	 * Logs the details of an API response, including the status code and body.
	 * 
	 * @param response The response object from the API call.
	 */
	public static void logResponse(Response response) {
		StringBuilder responseLog = new StringBuilder();
		responseLog.append("<b>Response Details:</b><br>");
		responseLog.append("Status Code: <b>").append(response.getStatusCode()).append("</b><br>");
		responseLog.append("Response Body: <pre>").append(response.asPrettyString()).append("</pre>");

		testThreadLocal.get().info(responseLog.toString()); // Log the response details
	}

	/**
	 * Logs a success message indicating that a test step has passed.
	 * 
	 * @param message The message to log.
	 */
	public static void logPass(String message) {
		testThreadLocal.get().pass("<b style='color: green;'>" + message + "</b>"); // Log the pass message
	}

	/**
	 * Logs a failure message for the current test step.
	 * 
	 * @param message The message to log.
	 */
	public static void logFail(String message) {
		testThreadLocal.get().fail("<b style='color: red;'>" + message + "</b>"); // Log the failure message
	}

	/**
	 * Logs a warning message to the current test in the report.
	 * 
	 * @param message The message to log.
	 */
	public static void logWarning(String message) {
		testThreadLocal.get().warning("<b style='color: orange;'>" + message + "</b>"); // Log the warning message
	}

	/**
	 * Logs an exception that occurred during the test.
	 * 
	 * @param throwable The exception to log.
	 */
	public static void logException(Throwable throwable) {
		testThreadLocal.get().fail("<b style='color: red;'>Exception occurred:</b> " + throwable.getMessage()); // Log
																												// the
																												// exception
																												// as a
																												// failure
	}

	/**
	 * Logs a custom message with HTML formatting.
	 * 
	 * @param message The custom message to log.
	 */
	public static void logCustomMessage(String message) {
		testThreadLocal.get().info("<b>" + message + "</b>"); // Log the custom message
	}

	/**
	 * Ends the current test and flushes the report to save all logged information.
	 * Cleans up the ThreadLocal storage.
	 */
	public static void endTest() {
		extent.flush(); // Save the report
		testThreadLocal.remove(); // Clean up the ThreadLocal storage
	}
}