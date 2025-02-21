package org.swapnil.scalablenotificaton.controllers;

import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.swapnil.scalablenotificaton.Models.BroadcastOfferRequest;
import org.swapnil.scalablenotificaton.Models.NotificationRequest;
import org.swapnil.scalablenotificaton.dtos.SmsRequest;
import org.swapnil.scalablenotificaton.services.KafkaService;
import org.swapnil.scalablenotificaton.services.NotificationProcessingService;
import org.swapnil.scalablenotificaton.services.SmsService;

@RestController
@RequestMapping("/api")
public class NotificationsController {

    private static final Logger log = LoggerFactory.getLogger(NotificationsController.class);

    private final KafkaService kafkaService;
    private final NotificationProcessingService notificationProcessingService;
    private final SmsService smsService;

    public NotificationsController(KafkaService kafkaService, NotificationProcessingService notificationProcessingService,
                                   SmsService smsService) {
        this.kafkaService = kafkaService;
        this.notificationProcessingService = notificationProcessingService;
        this.smsService = smsService;
    }

    @GetMapping("/health")
    public String getHealth() {
        return "Running";
    }

    // POST : http://localhost:8080/api/send-notification

    @PostMapping("/send-notification")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        try {
            // Validate the request
            notificationProcessingService.validateRequest(notificationRequest);

            // Assign priority if not set (assumed to be -1 when not set)
            if (notificationRequest.getNotificationPriority() == -1) {
                notificationProcessingService.assignPriority(notificationRequest);
            }

            // Send notification to Kafka
            kafkaService.sendNotification(notificationRequest);
            log.debug("Notification forwarded to Kafka Service with priority: " + notificationRequest.getNotificationPriority());
            return ResponseEntity.accepted().body("Notification accepted for processing.");
        } catch (ResponseStatusException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (KafkaException e) {
            log.error("Failed to forward notification to Kafka: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing notification.");
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // New endpoint for broadcast offers
    // POST : http://localhost:8080/api/broadcast-offer
    /*
            "emailSubject": "Limited Time Offer!",
            "message": "Get 50% off on your next purchase. Hurry up!"
     */
    @PostMapping("/broadcast-offer")
    public ResponseEntity<?> broadcastOffer(@RequestBody BroadcastOfferRequest broadcastOfferRequest) {
        if (broadcastOfferRequest.getEmailSubject() == null || broadcastOfferRequest.getEmailSubject().isEmpty() ||
                broadcastOfferRequest.getMessage() == null || broadcastOfferRequest.getMessage().isEmpty()) {
            return ResponseEntity.badRequest().body("Both subject and message are required for broadcast offers.");
        }
        try {
            kafkaService.sendBroadcastOffer(broadcastOfferRequest);
            log.debug("Broadcast offer forwarded to Kafka.");
            return ResponseEntity.accepted().body("Broadcast offer accepted for processing.");
        } catch (KafkaException e) {
            log.error("Failed to forward broadcast offer to Kafka: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing broadcast offer.");
        } catch (Exception e) {
            log.error("Unexpected error in broadcast offer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // SMS sender code
    //POST : http://localhost:8080/api/send-sms
    /*
        {
            "phoneNumber": "+919146102377",
            "messageBody": "Hello, this is a test message!"
        }
     */
    @PostMapping("/send-sms")
    public ResponseEntity<?> sendSms(@RequestBody SmsRequest smsRequest) {
        log.info("Received SMS request for {}", smsRequest.getPhoneNumber());
        smsService.sendSms(smsRequest.getPhoneNumber(), smsRequest.getMessageBody());
        return ResponseEntity.ok("SMS request received successfully");
    }

}
