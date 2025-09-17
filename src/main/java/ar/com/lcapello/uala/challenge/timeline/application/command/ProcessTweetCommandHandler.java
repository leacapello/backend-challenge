package ar.com.lcapello.uala.challenge.timeline.application.command;

import ar.com.lcapello.uala.challenge.timeline.domain.model.Timeline;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.FollowerReader;
import ar.com.lcapello.uala.challenge.timeline.domain.repository.TimelineCommandRepository;
import java.util.ArrayList;
import java.util.List;

public class ProcessTweetCommandHandler {

    private final FollowerReader followerReader;
    private final TimelineCommandRepository repository;

    public ProcessTweetCommandHandler(FollowerReader followerReader,
                                      TimelineCommandRepository repository) {
        this.followerReader = followerReader;
        this.repository = repository;
    }

    public void handle(CreateTweetEvent event) {
        final List<String> followerIds = followerReader.findFollowersOf(event.authorId());

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