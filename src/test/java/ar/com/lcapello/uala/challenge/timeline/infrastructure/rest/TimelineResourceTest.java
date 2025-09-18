package ar.com.lcapello.uala.challenge.timeline.infrastructure.rest;

import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerHandler;
import ar.com.lcapello.uala.challenge.slices.timeline.application.query.GetTimelineByFollowerQuery;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class TimelineResourceTest {

    @InjectMock
    GetTimelineByFollowerHandler handler;

    @Test
    void shouldReturnTimelineList() {
        // Arrange: armamos un Timeline válido (con followerId incluido)
        var tweetId = UUID.randomUUID().toString();
        var authorId = "author-123";
        var followerId = "user-123";
        var message = "Hola mundo";
        var createdAt = Instant.now();

        var timeline = new Timeline(tweetId, authorId, followerId, message, createdAt);

        when(handler.handle(any(GetTimelineByFollowerQuery.class)))
                .thenReturn(List.of(timeline));

        // Act + Assert
        given()
                .header("X-User-Id", followerId)
                .queryParam("page_size", 10)
                .queryParam("page", 2)
                .when()
                .get("/timeline")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1))
                .body("[0].tweetId", equalTo(tweetId))
                .body("[0].followedId", equalTo(authorId))
                .body("[0].message", equalTo(message))
                .body("[0].createdAt", notNullValue());
    }

    @Test
    @Disabled
    void shouldReturnEmptyListWhenNoTimelines() {
        when(handler.handle(any(GetTimelineByFollowerQuery.class)))
                .thenReturn(List.of());

        given()
                .header("X-User-Id", "user-999")
                .when()
                .get("/timeline")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(0));
    }

    @Test
    @Disabled
    void shouldWorkWithNullPageParams() {
        // Cuando no vienen page/page_size (null), igual debe responder 200
        when(handler.handle(any(GetTimelineByFollowerQuery.class)))
                .thenReturn(List.of());

        given()
                .header("X-User-Id", "user-abc")
                .when()
                .get("/timeline")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(0));
    }
}