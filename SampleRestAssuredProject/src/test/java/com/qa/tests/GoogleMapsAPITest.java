package com.qa.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.constants.APIEndPoint;
import com.qa.extentreport.ExtentReportManager;
import com.qa.jsonparser.JsonPathParser;

import io.restassured.response.Response;

public class GoogleMapsAPITest extends BaseTest {

	public GoogleMapsAPITest(String baseUri) {
		super("googlemapapi");

	}

	@Test
	public void TC001_getPlaceGoogleMaps_Scenario() {

		ExtentReportManager.startTest("Test GoogleMaps API: Get /get-place operation with valid data");

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("place_id", "841d6d7dddc14983222bb9be9286ece7");
		queryParams.put("key", "qaclick123");

		apiUtils.setBasePath(APIEndPoint.GOOGLEMAPS_PATH + "/get/json").withQueryParams(queryParams).get("");
		Response response = apiUtils.getResponse();

		logRequestAndResponse(APIEndPoint.GOOGLEMAPS_PATH + "/get/json", queryParams, response);
		validateResponse(response, 200, "Test passed: Status code is 200 as expected.");
		
		  String actLattitude = JsonPathParser.getStringValue(response.asString(),
		  "location.latitude"); String actLongitude =
		  JsonPathParser.getStringValue(response.asString(), "location.longitude");
		  
		  String actName = JsonPathParser.getStringValue(response.asString(), "name");
		  String actAddress = JsonPathParser.getStringValue(response.asString(),
		  "address"); String actWebsite =
		  JsonPathParser.getStringValue(response.asString(), "website");
		  
		  
		  assertEquals(actLattitude, "-38.383494",
				  "Actual Lattitude does not match with the expected Lattitude",
					"Lattitude details verified as expected.", "Lattitude details mismatched.");
		  assertEquals(actLongitude, "33.427362",
				  "Actual Longitude does not match with the expected Longitude",
				  "Longitude details verified as expected.", "Longitude details mismatched.");
		  assertEquals(actName, "Frontline house",
				  "Actual Place Name does not match with the expected Place Name",
				  "Name details verified as expected.", "Name details mismatched.");
		  assertEquals(actAddress, "29, side layout, cohen 09",
				  "Actual Place Address does not match with the expected Place Address",
				  "Address details verified as expected.", "Address details mismatched.");
		  assertEquals(actWebsite, "http://google.com",
				  "Actual WebSite URL does not match with the expected WebSite URL",
				  "Website details verified as expected.", "Website details mismatched.");

		//ExtentReportManager.logPass("Test passed: Place details verified as expected.");

	}

}
