package ar.com.lcapello.uala.challenge.tweet.infrastructure.rest;

import ar.com.lcapello.uala.challenge.common.domain.vo.UserID;
import ar.com.lcapello.uala.challenge.tweet.application.command.CreateTweetCommandHandler;
import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TweetResourceTest {

    @InjectMock
    CreateTweetCommandHandler createTweetCommandHandler;

    @Test
    void shouldCreateTweetAndReturnResponse() {
        // dado que el handler devuelve un Tweet creado
        Tweet fakeTweet = new Tweet(
                new TweetID("abc123"),
                new UserID("user-999"),
                "Hola mundo",
                Instant.parse("2025-09-15T20:00:00Z")
        );

        when(createTweetCommandHandler.handle(any())).thenReturn(fakeTweet);

        // cuando llamo al endpoint
        given()
                .header("X-User-Id", "user-999")
                .contentType(ContentType.JSON)
                .body("{\"message\": \"Hola mundo\"}")
                .when()
                .post("/tweets/abc123")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("tweetID", equalTo("abc123"))
                .body("authorID", equalTo("user-999"))
                .body("message", equalTo("Hola mundo"))
                .body("createdAt", equalTo("2025-09-15T20:00:00Z"))
                .log().body();
    }
}