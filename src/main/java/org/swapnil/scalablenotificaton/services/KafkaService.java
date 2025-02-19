package org.swapnil.scalablenotificaton.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.swapnil.scalablenotificaton.Models.BroadcastOfferRequest;
import org.swapnil.scalablenotificaton.Models.NotificationRequest;

import static org.swapnil.scalablenotificaton.constant.Constants.*;

@Service
public class KafkaService {

    private static final Logger log = LoggerFactory.getLogger(KafkaService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        try {
            String notification = prepareMessage(notificationRequest);
            String topic = getTopicByPriority(notificationRequest.getNotificationPriority());
            this.kafkaTemplate.send(topic, notification);
            log.info("Notification successfully forwarded to Kafka with priority: " + notificationRequest.getNotificationPriority());
        } catch (Exception e) {
            throw new KafkaException("Failed to send notification", e);
        }
    }

    private String prepareMessage(NotificationRequest notificationRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(notificationRequest);
    }

    private String getTopicByPriority(int priority) {
        switch (priority) {
            case 1:
                return TOPIC_PRIORITY_1;
            case 2:
                return TOPIC_PRIORITY_2;
            case 3:
                return TOPIC_PRIORITY_3;
            default:
                throw new IllegalArgumentException("Invalid priority: " + priority);
        }
    }
    // New method for broadcasting offers
    public void sendBroadcastOffer(BroadcastOfferRequest broadcastOfferRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String offerMessage = mapper.writeValueAsString(broadcastOfferRequest);
            this.kafkaTemplate.send(TOPIC_BROADCAST, offerMessage);
            log.info("Broadcast offer successfully forwarded to Kafka.");
        } catch (Exception e) {
            throw new KafkaException("Failed to send broadcast offer", e);
        }
    }
}
