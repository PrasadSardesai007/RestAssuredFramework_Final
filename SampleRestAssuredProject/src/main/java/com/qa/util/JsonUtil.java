package com.qa.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Method to read JSON file
	public static JsonNode readJsonFile(String filePath) throws IOException {
		return objectMapper.readTree(new File(filePath));
	}

	// Method to read and update the JSON file
	public static String readAndUpdateJson(String filePath, Map<String, String> updates) {
		JsonNode jsonNode;
		String jsonPath= System.getProperty("user.dir")+filePath;
		try {
			jsonNode = readJsonFile(filePath);
			// Convert JsonNode to a mutable Map
			Map<String, String> jsonMap = objectMapper.convertValue(jsonNode, HashMap.class);
			// Update the map with new values
			for (Map.Entry<String, String> entry : updates.entrySet()) {
				jsonMap.put(entry.getKey(), entry.getValue());
			}

			// Return the updated JSON as a string
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null; // Return null or handle as necessary if an error occurs
	}

}