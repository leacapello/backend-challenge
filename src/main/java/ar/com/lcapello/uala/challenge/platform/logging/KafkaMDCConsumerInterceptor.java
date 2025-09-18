package ar.com.lcapello.uala.challenge.platform.logging;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class KafkaMDCConsumerInterceptor implements ConsumerInterceptor<String, String> {

    private static final Logger LOG = Logger.getLogger(KafkaMDCConsumerInterceptor.class);

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        for (TopicPartition tp : records.partitions()) {
            records.records(tp).forEach(r -> {
                String requestId = UUID.randomUUID().toString();
                MDC.put("requestId", requestId);
                MDC.put("topic", r.topic());
                MDC.put("partition", String.valueOf(r.partition()));
                MDC.put("offset", String.valueOf(r.offset()));
                LOG.infof("Kafka consume topic=%s partition=%d offset=%d key=%s id=%s",
                        r.topic(), r.partition(), r.offset(), r.key(), requestId);
                MDC.clear();
            });
        }
        return records;
    }

    @Override public void configure(Map<String, ?> configs) {
    }

    @Override public void onCommit(Map<TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata> offsets) {
    }

    @Override public void close() {
    }

}