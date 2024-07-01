package RestTesting.RestTesting;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

public class UploadFileUsingAPI {
	long petId;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://petstore.swagger.io";
	}

	@Test
	public void uploadImageTest() {
	Response response = given().header("Content-Type", "multipart/form-data")
			.formParam("additionalMetadata", "Test")
			.multiPart("file", new File("C:\\download.png"), "image/png").when()
			.post("/v2/pet/9223372036854028716/uploadImage");	
		System.out.println(response.statusCode());
		System.out.println(response.asPrettyString());
	}
	//9223372036854028716
}
