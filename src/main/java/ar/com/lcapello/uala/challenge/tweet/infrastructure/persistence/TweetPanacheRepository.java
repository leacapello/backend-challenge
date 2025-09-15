package ar.com.lcapello.uala.challenge.tweet.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetCommandRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TweetPanacheRepository implements PanacheRepositoryBase<TweetEntity, String>, TweetCommandRepository {

    @Override
    @Transactional
    public void save(final Tweet tweet) {
        final TweetEntity entity = new TweetEntity();
        entity.setTweetID(tweet.getTweetID().value());
        entity.setAuthorID(tweet.getAuthorID().value());
        entity.setMessage(tweet.getMessage());
        entity.setCreatedAt(tweet.getCreatedAt());
        persist(entity);
    }

}