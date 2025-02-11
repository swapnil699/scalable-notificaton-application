package org.swapnil.scalablenotificaton.controllers;

import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.swapnil.scalablenotificaton.Models.NotificationRequest;
import org.swapnil.scalablenotificaton.services.KafkaService;
import org.swapnil.scalablenotificaton.services.NotificationProcessingService;

@RestController
@RequestMapping("/api")
public class NotificationsController {

    private static final Logger log = LoggerFactory.getLogger(NotificationsController.class);

    private final KafkaService kafkaService;
    private final NotificationProcessingService notificationProcessingService;

    public NotificationsController(KafkaService kafkaService, NotificationProcessingService notificationProcessingService) {
        this.kafkaService = kafkaService;
        this.notificationProcessingService = notificationProcessingService;
    }

    @GetMapping("/health")
    public String getHealth() {
        return "Running";
    }

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
}
