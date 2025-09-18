package ar.com.lcapello.uala.challenge.slices.timeline.domain.repository;

import java.util.List;

public interface FollowersReader {
    List<String> findFollowersOf(String authorId);
}
