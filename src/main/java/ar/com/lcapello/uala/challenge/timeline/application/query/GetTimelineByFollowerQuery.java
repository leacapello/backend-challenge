package ar.com.lcapello.uala.challenge.timeline.application.query;

public record GetTimelineByFollowerQuery(String followerId, Integer page, Integer pageSize) {
}
