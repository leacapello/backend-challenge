package ar.com.lcapello.uala.challenge.follower.infrastructure.rest;

import ar.com.lcapello.uala.challenge.follower.application.command.AddFollowCommandHandler;
import ar.com.lcapello.uala.challenge.follower.application.command.RemoveFollowCommandHandler;
import ar.com.lcapello.uala.challenge.follower.application.query.GetFollowersHandler;
import ar.com.lcapello.uala.challenge.follower.application.query.GetFollowingHandler;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class FollowerResourceTest {

    @Inject
    private AddFollowCommandHandler addFollowCommandHandler;
    @Inject
    private RemoveFollowCommandHandler removeFollowCommandHandler;
    @Inject
    private GetFollowersHandler getFollowersHandler;
    @Inject
    private GetFollowingHandler getFollowingHandler;

    @Test
    void shouldCreateFollowAndReturnResponse() {
        given()
                .header("X-User-Id", "user123")
                .when()
                .post("/followers/user456")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("followerId", equalTo("user123"))
                .body("followedId", equalTo("user456"))
                .body("createdAt", notNullValue());
    }

    @Test
    void shouldReturnFollowWhenExists() {
        // primero creo el follow
        given()
                .header("X-User-Id", "user111")
                .when()
                .post("/followers/user222")
                .then()
                .statusCode(201);

        // luego hago GET /followers/user222
        given()
                .header("X-User-Id", "user111")
                .when()
                .get("/followers/user222")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("followerId", equalTo("user111"))
                .body("followedId", equalTo("user222"))
                .body("createdAt", notNullValue());
    }

    @Test
    void shouldReturnListOfFollowers() {
        // creo dos usuarios que siguen a "me"
        given().header("X-User-Id", "f1").when().post("/followers/me").then().statusCode(anyOf(is(201), is(200)));
        given().header("X-User-Id", "f2").when().post("/followers/me").then().statusCode(anyOf(is(201), is(200)));

        // hago GET de mis followers
        given()
                .header("X-User-Id", "me")
                .when()
                .get("/followers")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()))
                .body("[0].followerId", notNullValue())
                .body("[0].followedId", equalTo("me"));
    }

    @Test
    void shouldDeleteFollow() {
        // creo el follow
        given()
                .header("X-User-Id", "userABC")
                .when()
                .post("/followers/userXYZ")
                .then()
                .statusCode(201);

        // lo elimino
        given()
                .header("X-User-Id", "userABC")
                .when()
                .delete("/followers/userXYZ")
                .then()
                .statusCode(204);

        // ahora debe devolver 404
        given()
                .header("X-User-Id", "userABC")
                .when()
                .get("/followers/userXYZ")
                .then()
                .statusCode(404);
    }
}