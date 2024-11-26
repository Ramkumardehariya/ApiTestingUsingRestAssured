package Swagger;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Swagger {
    @Test
    public void createPet() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 1,\n" + // Ensure the `id` is unique and valid
                        "  \"category\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"Dog\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 1,\n" +
                        "      \"name\": \"Friendly\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/pet");
        response.prettyPrint(); // Print response for debugging
        response.then().assertThat().statusCode(200); // Adjusted status code if necessary
    }

    @Test(dependsOnMethods = "createPet")
    public void uploadPetImage() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Perform the POST request to upload the image
        Response response = given()
                .header("accept", "application/json")
                .contentType("multipart/form-data")
                .multiPart("additionalMetadata", "Its a pet image")
                .multiPart("file", new java.io.File("C:\\Users\\ramku\\Downloads\\petImage.jpeg"), "image/jpeg")
                .when()
                .post("/pet/1/uploadImage");

        // Print the response for debugging purposes
        response.prettyPrint();

        // Verify the status code to check if the upload was successful
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void updatePet() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"puppy\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .put("https://petstore.swagger.io/v2/pet");
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getPet() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available");
        response.prettyPrint();
    }

    @Test
    public void postByPetId() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{ \"id\": 2, \"name\": \"Puff\", \"status\": \"available\" }") // Ensure the correct JSON structure
                .log().all() // Log the request for debugging
                .when()
                .post("https://petstore.swagger.io/v2/pet"); // Use `/pet` for creating/updating

        System.out.println("Status code: " + response.getStatusCode());
        response.prettyPrint(); // Print response body

        // Verify the status code (adjust if necessary)
        response.then().assertThat().statusCode(200); // Or use the actual returned status code
    }

    @Test
    public void getByPetId() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://petstore.swagger.io/v2/pet/2");
        response.prettyPrint();
    }

    @Test
    public void deletePetById() {
        Response response = given()
                .accept("application/json")
                .when()
                .delete("https://petstore.swagger.io/v2/pet/2");
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void postStore() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"petId\": 0,\n" +
                        "  \"quantity\": 0,\n" +
                        "  \"shipDate\": \"2024-11-18T09:24:06.590Z\",\n" +
                        "  \"status\": \"placed\",\n" +
                        "  \"complete\": true\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/store/order");
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getStore() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://petstore.swagger.io/v2/store/inventory");
        response.prettyPrint();
    }

    @Test
    public void getStoreById() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://petstore.swagger.io/v2/store/order/1");
        response.prettyPrint();
    }


    @Test
    public void CreateUser() {
        Response response = given().header("accept", " application/json").header("Content-Type", " application/json").body(

                "{\n" +
                        "  \"id\": 100,\n" +
                        "  \"username\": \"ram\",\n" +
                        "  \"firstName\": \"string\",\n" +
                        "  \"lastName\": \"string\",\n" +
                        "  \"email\": \"string\",\n" +
                        "  \"password\": \"12345\",\n" +
                        "  \"phone\": \"string\",\n" +
                        "  \"userStatus\": 0\n" +
                        "}"
        ).when().post("https://petstore.swagger.io/v2/user");
        response.prettyPrint();
        response.then().statusCode(200);
        Assert.assertEquals(200, response.statusCode());
    }
    @Test
    public  void UserLogin(){
        Response res=given().header("accept","application/json")
//                .queryParam("un","dharshanpl")
//                .queryParam("pass","12345")
                .pathParams("un", "ram", "pass", "12345")
                .when()
//                .get("https://petstore.swagger.io/v2/user/login");// using query parameters
                .get("https://petstore.swagger.io/v2/user/login?username={un}&password={pass}");// using path parameters
        res.prettyPrint();
    }
    @Test
    public  void Logout(){
        Response res=given().header("accept","application/json")

                .when()
                .get("https://petstore.swagger.io/v2/user/logout");
        res.prettyPrint();
    }
    @Test
    public void UpdateUser() {

        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 200,\n" +
                        "  \"username\": \"ram\",\n" +
                        "  \"firstName\": \"ram\",\n" +
                        "  \"lastName\": \"PL\",\n" +
                        "  \"email\": \"ram@gmail.com\",\n" +
                        "  \"password\": \"12345\",\n" +
                        "  \"phone\": \"9844480769\",\n" +
                        "  \"userStatus\": 250\n" +
                        "}")
                .when()
                .put("https://petstore.swagger.io/v2/user/dharshanpl");


        response.prettyPrint();


        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void GetUser() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://petstore.swagger.io/v2/user/ram");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void createUsers() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("[\n" +
                        "  {\n" +
                        "    \"id\": 101,\n" +
                        "    \"username\": \"john1\",\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"lastName\": \"Doe\",\n" +
                        "    \"email\": \"john.doe@example.com\",\n" +
                        "    \"password\": \"password123\",\n" +
                        "    \"phone\": \"1234567890\",\n" +
                        "    \"userStatus\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": 2,\n" +
                        "    \"username\": \"jane2\",\n" +
                        "    \"firstName\": \"Jane\",\n" +
                        "    \"lastName\": \"Doe\",\n" +
                        "    \"email\": \"jane.doe@example.com\",\n" +
                        "    \"password\": \"password456\",\n" +
                        "    \"phone\": \"0987654321\",\n" +
                        "    \"userStatus\": 1\n" +
                        "  }\n" +
                        "]")
                .when()
                .post("https://petstore.swagger.io/v2/user/createWithArray");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void createUser() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("[\n" +
                        "  {\n" +
                        "    \"id\": 1,\n" +
                        "    \"username\": \"john\",\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"lastName\": \"Doe\",\n" +
                        "    \"email\": \"john.doe@example.com\",\n" +
                        "    \"password\": \"password123\",\n" +
                        "    \"phone\": \"1234567890\",\n" +
                        "    \"userStatus\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": 2,\n" +
                        "    \"username\": \"jane\",\n" +
                        "    \"firstName\": \"Jane\",\n" +
                        "    \"lastName\": \"Doe\",\n" +
                        "    \"email\": \"jane.doe@example.com\",\n" +
                        "    \"password\": \"password456\",\n" +
                        "    \"phone\": \"0987654321\",\n" +
                        "    \"userStatus\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": 3,\n" +
                        "    \"username\": \"Alice\",\n" +
                        "    \"firstName\": \"Alice\",\n" +
                        "    \"lastName\": \"Smith\",\n" +
                        "    \"email\": \"alice.smith@example.com\",\n" +
                        "    \"password\": \"password789\",\n" +
                        "    \"phone\": \"1122334455\",\n" +
                        "    \"userStatus\": 0\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": 4,\n" +
                        "    \"username\": \"bob\",\n" +
                        "    \"firstName\": \"Bob\",\n" +
                        "    \"lastName\": \"Johnson\",\n" +
                        "    \"email\": \"bob.johnson@example.com\",\n" +
                        "    \"password\": \"password000\",\n" +
                        "    \"phone\": \"6677889900\",\n" +
                        "    \"userStatus\": 1\n" +
                        "  }\n" +
                        "]")
                .when()
                .post("https://petstore.swagger.io/v2/user/createWithList");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void deleteUser() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete("https://petstore.swagger.io/v2/user/ram");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }



}
