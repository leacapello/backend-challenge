package ar.com.lcapello.uala.challenge.tweet.infrastructure.rest;

import ar.com.lcapello.uala.challenge.tweet.application.command.CreateTweetCommandHandler;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class TweetResourceTest {

    @Inject
    CreateTweetCommandHandler createTweetCommandHandler;

    @Test
    void shouldCreateTweetAndReturnResponse() {
        final UUID tweetID = UUID.randomUUID();

        given()
            .header("X-User-Id", "user-999")
            .contentType(ContentType.JSON)
            .body("{\"message\": \"Hola mundo\"}")
        .when()
            .post("/tweets/" + tweetID.toString())
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("tweetID", equalTo(tweetID.toString()))
            .body("authorID", equalTo("user-999"))
            .body("message", equalTo("Hola mundo"))
            .body("createdAt", notNullValue());
    }

    @Test
    void shouldReturn400WithErrorBodyWhenMessageIsBlank() {
        given()
            .header("X-User-Id", "user-123")
            .contentType(ContentType.JSON)
            .body("{\"message\": \"\"}")
        .when()
            .post("/tweets/abc123")
        .then()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("error", equalTo("Invalid Tweet"))
            .body("message", equalTo("Message cannot be null or blank"));
    }

    @Test
    void shouldReturn400WhenTweetIdIsMissing() {
        given()
            .header("X-User-Id", "user-123")
            .contentType(ContentType.JSON)
            .body("{\"message\": \"hola mundo\"}")
        .when()
            .post("/tweets/ ")
        .then()
            .statusCode(404);
    }

}