package ar.com.lcapello.uala.challenge.tweet.infrastructure.config;

import ar.com.lcapello.uala.challenge.tweet.application.command.CreateTweetCommandHandler;
import ar.com.lcapello.uala.challenge.tweet.infrastructure.persistence.TweetPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class CommandHandlerFactory {

    @Produces
    @ApplicationScoped
    public CreateTweetCommandHandler createTweetCommandHandler(TweetPanacheRepository repository) {
        return new CreateTweetCommandHandler(repository);
    }

}