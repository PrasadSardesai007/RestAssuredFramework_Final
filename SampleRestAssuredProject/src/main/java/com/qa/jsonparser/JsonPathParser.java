package com.qa.jsonparser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;

public class JsonPathParser {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Parses the given JSON string and returns a JsonPath object.
	 *
	 * @param jsonString the JSON string to parse
	 * @return a JsonPath object representing the parsed JSON
	 */
	public static JsonPath parseJson(String jsonString) {

		try {
			return new JsonPath(jsonString);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Invalid Json String provided", e);
		}
	}

	/**
	 * Retrieves a String value from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the value to retrieve
	 * @return the String value associated with the key
	 */
	public static String getStringValue(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getString(key);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve String value for key: " + key, e);

		}

	}

	/**
	 * Retrieves an integer value from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the value to retrieve
	 * @return the integer value associated with the key
	 */
	public static int getIntValue(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getInt(key);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve Integer value for key:" + key, e);
		}
	}

	/**
	 * Retrieves a boolean value from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the value to retrieve
	 * @return the boolean value associated with the key
	 */
	public static boolean getBooleanValue(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getBoolean(key);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve the Boolean value for key:" + key, e);

		}
	}

	/**
	 * Retrieves a list of String values from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the value to retrieve
	 * @return a List of String values associated with the key
	 */
	public static List<String> getList(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getList(key);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve List for key:" + key, e);

		}
	}

	/**
	 * Retrieves the size of a list from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the list to check
	 * @return the size of the list associated with the key
	 */
	public static int getListSize(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getList(key).size();
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve size of list for key: " + key, e);
		}
	}

	/**
	 * Retrieves a map of String values from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the value to retrieve
	 * @return a Map of String values associated with the key
	 */
	public static Map<String, String> getMap(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getMap(key);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve Map for key: " + key, e);
		}
	}

	/**
	 * Checks if a specific key exists in the JSON.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key to check for existence
	 * @return true if the key exists, false otherwise
	 */
	public static boolean hasKey(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).get(key) != null;
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Error checking for Key:" + key, e);
		}
	}

	/**
	 * Retrieves a nested value from the JSON using the parent and child keys.
	 *
	 * @param jsonString the JSON string to parse
	 * @param parentKey  the parent key for the nested value
	 * @param childKey   the child key for the nested value
	 * @return the nested String value
	 */
	public static String getNestedValue(String jsonString, String parentKey, String childKey) {
		try {
			return new JsonPath(jsonString).getString(parentKey + "." + childKey);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve nested value for key:" + parentKey + ", " + childKey,
					e);
		}
	}

	/**
	 * Retrieves a value from a JSON array at a specific index.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the array
	 * @param index      the index of the value to retrieve
	 * @return the String value at the specified index
	 */
	public static String getArrayValueByIndex(String jsonString, String key, String index) {
		try {
			return new JsonPath(jsonString).getString(key + "[" + index + "]");
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve array value by key and index:" + key + ", " + index,
					e);
		}
	}

	/**
	 * Retrieves a list of Integer values from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the value to retrieve
	 * @return a List of Integer values associated with the key
	 */
	public static List<Integer> getIntList(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getList(key);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve list of Integer values for key:" + key, e);
		}
	}

	/**
	 * Retrieves a list of Boolean values from the JSON based on the provided key.
	 *
	 * @param jsonString the JSON string to parse
	 * @param key        the key of the value to retrieve
	 * @return a List of Boolean values associated with the key
	 */
	public static List<Boolean> getBooleanList(String jsonString, String key) {
		try {
			return new JsonPath(jsonString).getList(key);
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not retrieve list of boolean values for key:" + key, e);
		}
	}

	/**
	 * Pretty-prints the JSON string for better readability.
	 *
	 * @param jsonString the JSON string to print
	 * @return a pretty-printed version of the JSON string
	 */
	public static String prettyPrint(String jsonString) {
		try {
			return new JsonPath(jsonString).prettyPrint();
		} catch (JsonPathException e) {
			throw new IllegalArgumentException("Could not pretty print JSON string", e);

		}
	}

	 /**
     * Converts a REST Assured Response to a specified POJO class.
     *
     * @param response The REST Assured response.
     * @param clazz    The class of the POJO to convert to.
     * @param <T>      The type of the POJO.
     * @return An instance of the specified POJO or null if deserialization fails.
     */
    public <T> T convertResponseToPojo(Response response, Class<T> clazz) {
        // Get the JSON response as a string
        String jsonResponse = response.asString(); 
        try {
            // Convert the JSON response to the specified POJO class
            return objectMapper.readValue(jsonResponse, clazz);
        } catch (JsonMappingException e) {
            // Handle mapping exceptions (e.g., if the JSON structure doesn't match the POJO)
            System.err.println("JSON Mapping Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // Handle processing exceptions (e.g., issues during JSON parsing)
            System.err.println("JSON Processing Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if conversion fails
    }

    /**
     * Converts a given POJO to its JSON string representation.
     *
     * @param object The POJO object to convert.
     * @return The JSON string representation of the POJO.
     * @throws JsonProcessingException If the conversion fails.
     */
    public static String convertToJson(Object object) {
        try {
            // Convert the POJO to a JSON string
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exceptions
            System.err.println("JSON Processing Exception: " + e.getMessage());
             // Rethrow the exception for further handling
        }
		return null;
    }
}

