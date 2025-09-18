package ar.com.lcapello.uala.challenge.slices.timeline.infrastructure.messaging.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Map;

public record CreateTweetEventMsg(
        String tweetId,
        String authorId,
        String message,
        Instant createdAt
) {
    @JsonCreator
    public CreateTweetEventMsg(
            @JsonProperty("tweetID") Map<String, String> tweetID,
            @JsonProperty("authorID") String authorID,
            @JsonProperty("message") String message,
            @JsonProperty("createdAt") Instant createdAt
    ) {
        this(
                tweetID != null ? tweetID.get("value") : null,
                authorID,
                message,
                createdAt
        );
    }
}
