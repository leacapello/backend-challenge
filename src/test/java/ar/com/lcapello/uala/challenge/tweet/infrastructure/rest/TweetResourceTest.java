package ar.com.lcapello.uala.challenge.tweet.infrastructure.rest;

import ar.com.lcapello.uala.challenge.tweet.application.command.CreateTweetCommandHandler;
import ar.com.lcapello.uala.challenge.tweet.infrastructure.lifecycle.KafkaTestLifecycleManagerOut;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(KafkaTestLifecycleManagerOut.class)
public class TweetResourceTest {

    @Inject
    CreateTweetCommandHandler createTweetCommandHandler;

    @Test
    void shouldGetTweetAndReturnResponse() {
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

    @Test
    void shouldReturnTweetWhenExists() {
        String tweetID = UUID.randomUUID().toString();

        given()
                .header("X-User-Id", "user-123")
                .contentType(ContentType.JSON)
                .body("{\"message\": \"Hola desde GET\"}")
                .when()
                .post("/tweets/" + tweetID)
                .then()
                .statusCode(201);

        // luego hago el GET
        given()
            .header("X-User-Id", "user-123")
        .when()
            .get("/tweets/" + tweetID)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("tweetID", equalTo(tweetID))
            .body("authorID", equalTo("user-123"))
            .body("message", equalTo("Hola desde GET"))
            .body("createdAt", notNullValue());
    }

    @Test
    void shouldReturn404WhenTweetDoesNotExist() {
        given()
            .header("X-User-Id", "user-123")
        .when()
            .get("/tweets/" + UUID.randomUUID())
        .then()
            .statusCode(404);
    }

}