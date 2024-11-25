package com.qa.requestspecifications;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.util.ConfigReader;

public class ApiUtils {

	// Static variable to hold the request specification.
	private static RequestSpecification requestSpecification;
	private static String configPath = System.getProperty("user.dir")
			+ "//src//test//resources//Config//Config.properties";
	static Properties prop;
	private static Response response;

	private static final Logger logger = Logger.getLogger(ApiUtils.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public ApiUtils() {

		requestSpecification = RestAssured.given();
	}

	public static ApiUtils init() {

		return new ApiUtils();
	}

	/**
	 * Retrieves the base URI for a specified API based on the execution
	 * environment.
	 * 
	 * @param apiName The name of the API to get the base URI for.
	 * @return The base URI as a String if found; otherwise, returns null.
	 */

	public static String getUrl(String apiName) {
		ConfigReader configReader = new ConfigReader();
		String baseUriUpd = ""; // Declare without initialization
		try {
			prop = configReader.init_prop(configPath);
			String env = prop.getProperty("ExecutionEnvironment");

			// Construct the base URI based on the environment
			String baseUriKey = env.toLowerCase() + "." + apiName.toLowerCase() + ".baseUri";
			baseUriUpd = prop.getProperty(baseUriKey);

			if (baseUriUpd == null || baseUriUpd.isEmpty()) {

				logger.error("Incorrect Base URI given for key: " + baseUriKey);

			} else {
				logger.info("Base URI for " + apiName + " is: " + baseUriUpd);
				return baseUriUpd;
			}
		} catch (Exception e) {
			System.out.println("Unable to get BaseUri from Config" + e.getMessage());
		}
		return baseUriUpd; // This will return null if not found
	}

	/**
	 * Sets the base URI for RestAssured based on the specified execution
	 * environment retrieved from configuration properties. Constructs the URI in
	 * the format "<environment>.<baseUri>" for recognized environments (SIT, UAT,
	 * DEV) and handles invalid environments by printing an error message.
	 * 
	 * @param baseUri: Passing API name to get the appropriate baseUri from Config
	 *                 file (e.g. LibraryAPI)
	 */

	public ApiUtils setBaseUri(String baseUri) {

		String baseUrl = getUrl(baseUri);
		requestSpecification.baseUri(baseUrl);
		logger.info("Base URI set to: " + baseUrl);
		return this;
	}

	/**
	 * This method will just return the baseUri
	 * 
	 * @param baseUri:Passing API name to get the appropriate baseUri from Config
	 *                        file (e.g. LibraryAPI)
	 * @return baseUri as String
	 */
	public String getBaseUri(String baseUri) {

		return getUrl(baseUri);
	}

	/**
	 * Sets the base path for all subsequent API requests.
	 *
	 * @param basePath The base path to be set (e.g., "/v1").
	 */
	public ApiUtils setBasePath(String basePath) {
		requestSpecification.basePath(basePath);
		logger.info("Base path set to: " + basePath);
		return this;

	}

	/**
	 * Adds a single query parameter to the request specification.
	 *
	 * @param key   The name of the query parameter.
	 * @param value The value of the query parameter.
	 * @return A RequestSpecification object with the query parameter applied, or
	 *         the original specification if the key or value is null.
	 */
	public ApiUtils withQueryParam(String key, String value) {
		// requestSpecification = RestAssured.given(); // Create a new request
		// specification.

		// Check if both key and value are not null before adding the query parameter
		if (key != null && value != null) {
			requestSpecification.queryParam(key, value); // Add the query parameter.
		}

		return this; // Return the modified RequestSpecification.
	}

	/**
	 * Initializes the request specification and adds query parameters.
	 *
	 * @param queryParams A map containing query parameters and their values.
	 * @return A RequestSpecification object with query parameters applied.
	 */
	public ApiUtils withQueryParams(Map<String, String> queryParams) {
		//requestSpecification = RestAssured.given(); // Create a new request specification.
		if (queryParams != null) {
			requestSpecification.queryParams(queryParams); // Add query parameters if provided.
		}
		return this; // Return the modified RequestSpecification.
	}

	/**
	 * Adds a single path parameter to the current request specification.
	 *
	 * @param key   The name of the path parameter.
	 * @param value The value of the path parameter.
	 * @return A RequestSpecification object with the path parameter applied, or the
	 *         original specification if the key or value is null.
	 */
	public ApiUtils withPathParam(String key, String value) {
		// Check if both key and value are not null before adding the path parameter
		if (key != null && value != null) {
			requestSpecification.pathParam(key, value); // Add the path parameter.
		}
		return this; // Return the modified RequestSpecification.
	}

	/**
	 * Adds path parameters to the current request specification.
	 *
	 * @param pathParams A map containing path parameters and their values.
	 * @return A RequestSpecification object with path parameters applied.
	 */
	public ApiUtils withPathParams(Map<String, String> pathParams) {
		if (pathParams != null) {
			requestSpecification.pathParams(pathParams); // Add path parameters if provided.
		}
		return this; // Return the modified RequestSpecification.
	}

	/**
	 * Sets the body content for POST or PUT requests.
	 *
	 * @param body The content to be sent in the request body.
	 * @return A RequestSpecification object with the body set.
	 */
	public ApiUtils withBody(Object body) {
		requestSpecification.body(body); // Set the request body.
		return this; // Return the modified RequestSpecification.
	}

	/**
	 * Adds Basic Authentication to the request specification.
	 *
	 * @param username The username for authentication.
	 * @param password The password for authentication.
	 * @return A RequestSpecification object with Basic Auth applied.
	 */
	public ApiUtils withBasicAuth(String username, String password) {
		requestSpecification = requestSpecification.auth().preemptive().basic(username, password); // Add Basic Auth.
		return this;
	}

	/**
	 * Adds OAuth 2.0 Bearer Token authentication to the request specification.
	 *
	 * @param token The OAuth 2.0 Bearer token for authentication.
	 * @return A RequestSpecification object with OAuth 2.0 applied.
	 */
	public static RequestSpecification withOAuth2(String token) {
		return requestSpecification.auth().oauth2(token); // Add OAuth 2.0 Bearer Token.
	}

	/**
	 * Executes a GET request to the specified endpoint.
	 *
	 * @param endpoint
	 * @return A Response object containing the response from the server.
	 */

	public ApiUtils get(String endpoint) {
		logger.info("Executing GET request to: " + endpoint);
		response = requestSpecification.get(endpoint);
		logResponse(response);
		return this;

	}

	/**
	 * Executes a POST request to the specified endpoint.
	 *
	 * @param endpoint The endpoint to which the POST request is sent.
	 * @param body     The content to be sent in the request body.
	 * @return A Response object containing the response from the server.
	 */
	public ApiUtils post() {
		//requestSpecification = RestAssured.given();
		logger.info("Executing POST request ");
		 response = requestSpecification.post(); // Set the body and execute
																							// the POST
		logResponse(response);
		return this; // request.
	}

	/**
	 * Executes a PUT request to the specified endpoint.
	 *
	 * @param endpoint The endpoint to which the PUT request is sent.
	 * @param body     The content to be sent in the request body.
	 * @return A Response object containing the response from the server.
	 */

	/**
	 * Executes a DELETE request to the specified endpoint.
	 *
	 * @param endpoint The endpoint to which the DELETE request is sent.
	 * @return A Response object containing the response from the server.
	 */
	public static Response delete(String endpoint) {
		return requestSpecification.when().delete(endpoint); // Execute the DELETE request.
	}

	/**
	 * Adds custom headers to the request specification.
	 *
	 * @param headers A map containing headers and their values.
	 * @return A RequestSpecification object with headers applied.
	 */
	public static RequestSpecification withHeaders(Map<String, String> headers) {
		if (headers != null) {
			requestSpecification.headers(headers); // Add headers if provided.
		}
		return requestSpecification; // Return the modified RequestSpecification.
	}

	/**
	 * Sets the Content-Type header for the request.
	 *
	 * @param contentType The Content-Type to be set (e.g., "application/json").
	 * @return A RequestSpecification object with the Content-Type header set.
	 */
	public static RequestSpecification withContentType(String contentType) {
		return requestSpecification.contentType(contentType); // Set the Content-Type header.
	}

	/**
	 * Resets the request specification to a clean state. This is useful for
	 * starting a new request without retaining previous configurations.
	 */
	public static void resetRequestSpecification() {
		requestSpecification = null; // Clear the request specification.
	}

	/**
	 * Convert REST Assured Response to a specified POJO class.
	 *
	 * @param response The REST Assured response.
	 * @param clazz    The class of the POJO to convert to.
	 * @param <T>      The type of the POJO.
	 * @return An instance of the specified POJO.
	 * @throws IOException If deserialization fails.
	 */
	public <T> T convertResponseToPojo(Response response, Class<T> clazz) {
		String jsonResponse = response.asString();
		try {
			return objectMapper.readValue(jsonResponse, clazz);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Response getResponse() {
		return response;
	}

	private static void logResponse(Response response) {
		logger.info("Response Status Code: " + response.getStatusCode());
		logger.info("Response Body: ");
		logger.info(response.getBody().asPrettyString());
	}

}