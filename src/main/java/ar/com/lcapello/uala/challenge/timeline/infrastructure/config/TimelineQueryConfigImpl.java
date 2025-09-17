package ar.com.lcapello.uala.challenge.timeline.infrastructure.config;

import ar.com.lcapello.uala.challenge.timeline.application.config.TimelineQueryConfig;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TimelineQueryConfigImpl implements TimelineQueryConfig {

    @ConfigProperty(name = "timeline.max-page-size")
    private int maxPageSize;

    @Override
    public int maxPageSize() {
        return maxPageSize;
    }

}