package ar.com.lcapello.uala.challenge.slices.timeline.domain.repository;

import java.util.List;

public interface FollowerReader {
    List<String> findFollowersOf(String authorId);
}
