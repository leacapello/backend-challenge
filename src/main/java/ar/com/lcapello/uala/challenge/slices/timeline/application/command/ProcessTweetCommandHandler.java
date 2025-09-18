package ar.com.lcapello.uala.challenge.slices.timeline.application.command;

import ar.com.lcapello.uala.challenge.slices.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.FollowersReader;
import ar.com.lcapello.uala.challenge.slices.timeline.domain.repository.TimelineCommandRepository;
import java.util.ArrayList;
import java.util.List;

public class ProcessTweetCommandHandler {

    private final FollowersReader followersReader;
    private final TimelineCommandRepository repository;

    public ProcessTweetCommandHandler(FollowersReader followersReader,
                                      TimelineCommandRepository repository) {
        this.followersReader = followersReader;
        this.repository = repository;
    }

    public void handle(ProcessTweetCommand event) {
        final List<String> followerIds = followersReader.findFollowersOf(event.authorId());

        final List<Timeline> entries = new ArrayList<>(followerIds.size());

        for (final String followerId : followerIds) {
            entries.add(new Timeline(
                    event.tweetId(),
                    event.authorId(),
                    followerId,
                    event.message(),
                    event.createdAt()
            ));
        }

        repository.saveAll(entries);
    }
}