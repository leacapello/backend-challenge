package ar.com.lcapello.uala.challenge.tweet.infrastructure.persistence;

import ar.com.lcapello.uala.challenge.tweet.domain.model.Tweet;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetCommandRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.repository.TweetQueryRepository;
import ar.com.lcapello.uala.challenge.tweet.domain.vo.TweetID;
import ar.com.lcapello.uala.challenge.user.domain.vo.UserID;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class TweetPanacheRepository implements PanacheRepositoryBase<TweetEntity, String>, TweetCommandRepository, TweetQueryRepository {

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

    @Override
    public Optional<Tweet> findById(TweetID tweetID) {
        return findByIdOptional(tweetID.value())
                .map(entity -> new Tweet(
                        new TweetID(entity.getTweetID()),
                        new UserID(entity.getAuthorID()),
                        entity.getMessage(),
                        entity.getCreatedAt()
                ));
    }

}