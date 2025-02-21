package org.swapnil.scalablenotificaton.services;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.swapnil.scalablenotificaton.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import com.twilio.rest.api.v2010.account.Message;




@Service
public class SmsService {
    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public SmsService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String phoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(phoneNumber),
                    new com.twilio.type.PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();

            kafkaTemplate.send(Constants.TOPIC_NOTIFICATIONS, String.valueOf(message));
            log.info("SMS request published to Kafka for phone: {}", phoneNumber);
        } catch (Exception e) {
            throw new KafkaException("Failed to publish SMS request to Kafka", e);
        }
    }
}
