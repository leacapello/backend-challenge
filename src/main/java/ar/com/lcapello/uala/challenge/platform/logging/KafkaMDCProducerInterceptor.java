package ar.com.lcapello.uala.challenge.platform.logging;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class KafkaMDCProducerInterceptor implements ProducerInterceptor<String, String> {

    private static final Logger LOG = Logger.getLogger(KafkaMDCProducerInterceptor.class);

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        String requestId = String.valueOf(MDC.get("requestId"));
        if (requestId == null || requestId.equals("null") || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
        LOG.infof("Kafka produce topic=%s key=%s id=%s", record.topic(), record.key(), requestId);
        return record;
    }

    @Override public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
    }

    @Override public void close() {
    }

    @Override public void configure(Map<String, ?> configs) {
    }

}