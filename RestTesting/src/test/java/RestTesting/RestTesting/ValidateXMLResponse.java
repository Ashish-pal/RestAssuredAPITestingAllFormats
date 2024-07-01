package RestTesting.RestTesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ValidateXMLResponse {
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://petstore.swagger.io";
	}
	
	@Test
	public void validateXMLTest() {
		String jsonBody = "'{" 
						+ "  \"id\": 0,"
						+ "  \"category\": {" 
						+ "    \"id\": 0," 
						+ "    \"name\": \"PetCats\""
						+ "  }," 
						+ "  \"name\": \"PetCat\"," 
						+ "  \"photoUrls\": [" 
						+ "    \"string\"" 
						+ "  ],"
						+ "  \"tags\": [" 
						+ "    {" 
						+ "      \"id\": 0," 
						+ "      \"name\": \"Tag1\"" 
						+ "    }" 
						+ "  ],"
						+ "  \"status\": \"available\"" 
						+ "}'";

		Response response = given()
				.header("accept", "application/xml")
				.header("Content-Type", "application/json")
				.body(jsonBody).when().post("/v2/pet/");
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.contentType(), "application/xml");
		
		
		response.then()
		.body("category.name", equalTo("PetCats"))
		.body("pet.name",equalTo("PetCats"))
		.and().body("pet.status", equalTo("available"));
	}

}
