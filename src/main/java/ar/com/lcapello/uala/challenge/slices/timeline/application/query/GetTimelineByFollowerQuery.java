package ar.com.lcapello.uala.challenge.slices.timeline.application.query;

public record GetTimelineByFollowerQuery(String followerId, Integer page, Integer pageSize) {
}
