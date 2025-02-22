package RestTesting.RestTesting;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APIChainingTest {
	long petId;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://petstore.swagger.io";
	}

	@Test
	public void getPetsPendingAPI() {
		Response responses = given().header("accept", "application/json").when()
				.get("/v2/pet/findByStatus?status=available");
		System.out.println(responses.asPrettyString());
		Assert.assertEquals(responses.statusCode(), 200);
		Assert.assertEquals(responses.contentType(), "application/json");
	}

	@Test
	public void postPetAPITest() {
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

		Response response = given().header("accept", "application/json").header("Content-Type", "application/json")
				.body(jsonBody).when().post("/v2/pet/");
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.contentType(), "application/json");
		response.then().body("category.id", equalTo(0))
		.body("category.name", equalTo("PetCats"))
		.body("name",equalTo("PetCats"))
		.body("tags[0].id",equalTo(0))
		.body("tags[0].name",equalTo("Tags1"))
		.and().body("status", equalTo("available"));
		
		petId = response.path("id");
		System.out.println(petId);
	}

	@Test(dependsOnMethods = {"postPetAPITest"})
	public void putPetAPITest() {
		String jsonBody = "{" 
				+ "  \"id\": "+petId+"," 
				+ "  \"category\": {" 
				+ "    \"id\": 0,"
				+ "    \"name\": \"string\"" 
				+ "  }," 
				+ "  \"name\": \"doggieeeee\"," 
				+ "  \"photoUrls\": ["
				+ "    \"string\"" 
				+ "  ]," 
				+ "  \"tags\": [" 
				+ "    {" 
				+ "      \"id\": 0,"
				+ "      \"name\": \"string\"" 
				+ "    }" 
				+ "  ]," 
				+ "  \"status\": \"available\"" 
				+ "}'";

		Response response = given().header("accept", "application/json").header("Content-Type", "application/json")
				.body(jsonBody).when().put("/v2/pet/");
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.contentType(), "application/json");
	}

	@Test(dependsOnMethods = {"putPetAPITest"})
	public void deletePetAPITest() {
		Response response = given().header("accept", "application/json")
		.when().delete("/v2/pet/"+petId);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.contentType(), "application/json");
	}
	
	@Test(dependsOnMethods = {"postPetAPITest"})
	public void postUpdateAPITest() {
		Response response = given().header("accept", "application/json").header("Content-Type","application/x-www-form-urlencoded").
				formParam("name","NewDogs").formParam("status", "sold").
				when().post("/v2/pet/"+petId);
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.contentType(), "application/json");
	}
}
