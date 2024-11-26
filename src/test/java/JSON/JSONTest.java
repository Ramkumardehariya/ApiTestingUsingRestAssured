package JSON;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

@Test
public class JSONTest {
    @Test
    public void createPost() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"id\": \"2\",\n" +
                        "    \"title\": \"New API Post\",\n" +
                        "    \"author\": \"John Doe\"\n" +
                        "}")
                .when()
                .post("http://localhost:3000/posts");
                response.then().assertThat().statusCode(201);
    }

    @Test
    public void getPosts() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("http://localhost:3000/posts");
        response.prettyPrint();
    }

    @Test
    public void updatePost() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"id\": \"1\",\n" +
                        "    \"title\": \"Api tester\",\n" +
                        "    \"author\": \"Ram\"\n" +
                        "}")
                .when()
                .put("http://localhost:3000/posts/4");
                response.then().assertThat().statusCode(200);
    }
    @Test
    public void deletePost() {
        Response response = given()
                .accept("application/json")
                .when()
                .delete("http://localhost:3000/posts/2");
                response.then().assertThat().statusCode(200);
    }


    @Test
    public void createComments() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"id\": \"2\",\n" +
                        "    \"body\": \"some comment\",\n" +
                        "    \"postId\": \"2\"\n" +
                        "}")
                .when()
                .post("http://localhost:3000/comments");
        response.then().assertThat().statusCode(201);
    }

    @Test
    public void getComments() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("http://localhost:3000/comments");
        response.prettyPrint();
    }

    @Test
    public void updateComments() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"id\": \"3\",\n" +
                        "    \"body\": \"Api tester\",\n" +
                        "    \"postId\": \"Ram\"\n" +
                        "}")
                .when()
                .put("http://localhost:3000/posts/2");
        response.then().assertThat().statusCode(200);
    }
    @Test
    public void deleteComments() {
        Response response = given()
                .accept("application/json")
                .when()
                .delete("http://localhost:3000/posts/2");
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void createProfile() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"id\": \"3\",\n" +
                        "    \"name\": \"Darshan\"\n" +
                        "}")
                .when()
                .post("http://localhost:3000/profile");
        response.then().assertThat().statusCode(201);
    }


    @Test
    public void getProfile() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("http://localhost:3000/profile");
        response.prettyPrint();
    }

    @Test
    public void updateProfile() {
        Response response = given()
                .accept("application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"id\": \"3\",\n" +
                        "    \"name\": \"Rajkumar patil\"\n" +
                        "}")
                .when()
                .put("http://localhost:3000/profile/3");
        response.then().assertThat().statusCode(200);
    }
    @Test
    public void deleteProfile() {
        Response response = given()
                .accept("application/json")
                .when()
                .delete("http://localhost:3000/profile/3");
        response.then().assertThat().statusCode(200);
    }
}
