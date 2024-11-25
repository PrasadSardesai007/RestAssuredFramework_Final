/*
 * package com.qa.service;
 * 
 * 
 * import com.qa.constants.APIEndPoint; import
 * com.qa.requestspecifications.ApiUtils;
 * 
 * import io.restassured.response.Response;
 * 
 * public class LibraryAPIService {
 * 
 * private Response response;
 * 
 * public static LibraryAPIService init() {
 * 
 * return new LibraryAPIService(); }
 * 
 * public LibraryAPIService getBookByAuthorName() {
 * 
 * ApiUtils apiUtils = ApiUtils.init().setBaseUri("libraryapi")
 * .setBasePath(APIEndPoint.LIBRARY_PATH +
 * "/GetBook.php").withQueryParam("AuthorName", "Rajesh P").get("");
 * 
 * return this; }
 * 
 * public Response apiResponse() { return this.response =
 * ApiUtils.init().apiResponse(); }
 * 
 * }
 */