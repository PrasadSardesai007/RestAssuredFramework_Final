package com.qa.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.constants.APIEndPoint;
import com.qa.extentreport.ExtentReportManager;
import com.qa.jsonparser.JsonPathParser;
import com.qa.pojo.Library;
import com.qa.requestspecifications.ApiUtils;

import io.restassured.response.Response;

public class LibraryAPITest extends BaseTest {

	public LibraryAPITest() {
		super("libraryapi");
	}

	/**
	 * API: Library API Scenario:TC001_getBookByAuthorName_positiveScenario
	 * 
	 * Description: Verify GET / get-book-by-authorname operation with valid data
	 * input
	 * 
	 */

	@Test
	public void TC001_getBookByAuthorName_positiveScenario() {

		ExtentReportManager.startTest("Test Library API: Get /book-by-authorname operation with valid data");

		apiUtils.setBasePath(APIEndPoint.LIBRARY_PATH + "/GetBook.php")
				.withQueryParam("AuthorName", "Vaibhavi Sardesai").get("");
		Response response = apiUtils.getResponse();

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("AuthorName", "Vaibhavi Sardesai");

		logRequestAndResponse(APIEndPoint.LIBRARY_PATH + "/GetBook.php", queryParams, response);
		validateResponse(response, 200, "Test passed: Status code is 200 as expected.");
		String actualBookName = JsonPathParser.getStringValue(response.asString(), "[0].book_name");

		assertEquals(actualBookName, "My Gujrati Bookk", "Actual Book Name does not match with the expected book",
				"Book details verified as expected.", "Book details mismatched.");

	}

	/**
	 * API: Library API Scenario:TC002_getBookByAuthorName_NegativeScenario
	 * 
	 * Description: Verify GET / get-book-by-authorname operation with invalid data
	 * input
	 * 
	 */

	@Test
	public void TC002_getBookByAuthorName_NegativeScenario() {

		ExtentReportManager.startTest("Test Library API: Get /book-by-authorname operation with invalid data");

		apiUtils.setBasePath(APIEndPoint.LIBRARY_PATH + "/GetBook.php").withQueryParam("AuthorName", "Donald Trump")
				.get("");
		Response response = apiUtils.getResponse();

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("AuthorName", "Donald Trump");

		logRequestAndResponse(APIEndPoint.LIBRARY_PATH + "/GetBook.php", queryParams, response);
		validateResponse(response, 404, "Test passed: Status code is 404 as expected.");

		String actualMsg = JsonPathParser.getStringValue(response.asString(), "msg");

		assertEquals(actualMsg, "The book by requested bookid / author name does not existss!",
				"Actual Message does not match with the expected Message", "Message content verified as expected.",
				"Message Content mismatched.");

	}

	/**
	 * API: Library API Scenario:TC003_addBookScenario
	 * 
	 * Description: Verify POST / add-book operation with valid data input
	 * 
	 */

	@Test
	public void TC003_addBook_Scenario() {

		ExtentReportManager.startTest("Test Library API: POST /add-book operation with valid data");

		Library libraryObj = new Library("My Test Book", "auv012", "879489", "C B Sardesai");
		String request = JsonPathParser.convertToJson(libraryObj);
		apiUtils.setBasePath(APIEndPoint.LIBRARY_PATH + "/Addbook.php").withBody(request).post();
		Response response = apiUtils.getResponse();

		logRequestAndResponse(APIEndPoint.LIBRARY_PATH + "/AddBook.php", request, response);
		validateResponse(response, 200, "Test passed: Status code is 200 as expected.");
		String actualMsg = JsonPathParser.getStringValue(response.asString(), "Msg");

		assertEquals(actualMsg, "successfully added", "Actual Message does not match with the expected Message",
				"Message content verified as expected.", "Message Content mismatched.");

		String actualId = JsonPathParser.getStringValue(response.asString(), "ID");
		Assert.assertNotNull(actualId, "ID field should not be Null");

		String deleteReq = String.format("{ \"ID\": \"%s\" }", actualId);
		ApiUtils apiUtilss = ApiUtils.init().setBaseUri("libraryapi")
				.setBasePath(APIEndPoint.LIBRARY_PATH + "/DeleteBook.php").withBody(deleteReq).post();

	}

	/**
	 * API: Library API Scenario:TC004_deleteBookScenario
	 * 
	 * Description: Verify POST / delete-book operation with valid data input
	 * 
	 */

	@Test
	public void TC004_deleteBook_Scenario() {

		String actualId = "";

		ExtentReportManager.startTest("Test Library API: POST /delete-book operation with valid data");
		Library libraryObj = new Library("My Test Book", "auv012", "879489", "C B Sardesai");
		
		String request = JsonPathParser.convertToJson(libraryObj);
		apiUtils.setBasePath(APIEndPoint.LIBRARY_PATH + "/Addbook.php").withBody(request).post();
		Response response = apiUtils.getResponse();
		logRequestAndResponse(APIEndPoint.LIBRARY_PATH + "/AddBook.php", request, response);
		validateResponse(response, 200, "Test passed: Status code is 200 as expected.");
		String actualMsg = JsonPathParser.getStringValue(response.asString(), "Msg");

		assertEquals(actualMsg, "successfully added", "Actual Message does not match with the expected Message",
				"Message content verified as expected.", "Message Content mismatched.");

		actualId = JsonPathParser.getStringValue(response.asString(), "ID");
		Assert.assertNotNull(actualId, "ID field should not be Null");

		String deleteReq = String.format("{ \"ID\": \"%s\" }", actualId);
		ApiUtils apiUtilss = ApiUtils.init().setBaseUri("libraryapi")
				.setBasePath(APIEndPoint.LIBRARY_PATH + "/DeleteBook.php").withBody(deleteReq).post();

		Response response2 = apiUtilss.getResponse();
		logRequestAndResponse(APIEndPoint.LIBRARY_PATH + "/DeleteBook.php", deleteReq, response2);
		validateResponse(response, 200, "Test passed: Status code is 200 as expected.");

		String actualMsg2 = JsonPathParser.getStringValue(response2.asString(), "msg");

		assertEquals(actualMsg2, "book is successfully deleted",
				"Actual Message does not match with the expected Message", "Message content verified as expected.",
				"Message Content mismatched.");

	}

}
