package org.swapnil.scalablenotificaton.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.swapnil.scalablenotificaton.Models.Content;
import org.swapnil.scalablenotificaton.Models.NotificationRequest;
import org.swapnil.scalablenotificaton.Models.Recipient;
import org.swapnil.scalablenotificaton.Models.Template;
import org.swapnil.scalablenotificaton.exceptions.TemplateNotFoundException;
import org.swapnil.scalablenotificaton.repositorys.TemplateRepository;

import java.util.Arrays;

@Service
public class NotificationProcessingService {

    private static final Logger log = LoggerFactory.getLogger(NotificationProcessingService.class);

    private final RedisService redisService;
    private final TemplateRepository templateRepository;

    public NotificationProcessingService(RedisService redisService, TemplateRepository templateRepository){
        this.redisService = redisService;
        this.templateRepository = templateRepository;
    }

    /*
     * Checks:
     * - Priority must be -1,1,2 or 3.
     * - Channels must include at least one of: sms, email, or push.
     * - Recipient’s userId must be non-empty.
     * - Content must contain a message body unless using a template.
     */
    public void validateRequest(NotificationRequest notificationRequest) {
        int priority = notificationRequest.getNotificationPriority();
        if (priority < -1 || priority == 0 || priority > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad priority request.");
        }

        String[] channels = notificationRequest.getChannels();
        if (channels == null || (!Arrays.asList(channels).contains("sms")
                && !Arrays.asList(channels).contains("push")
                && !Arrays.asList(channels).contains("email"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad channels request");
        }

        Recipient recipient = notificationRequest.getRecipient();
        if (recipient == null || recipient.getUserId() == null || recipient.getUserId().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request recipient.");
        }

        Content content = notificationRequest.getContent();
        if (content == null || ((content.getMessage() == null || content.getMessage().isEmpty())
                && !content.isUsingTemplates())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request content.");
        }
    }

    /*
     * If not using a template, assign medium priority (2).
     */
    public void assignPriority(NotificationRequest notificationRequest) {
        if (!notificationRequest.getContent().isUsingTemplates()) {
            notificationRequest.setNotificationPriority(2);
        } else {
            assignPriorityWithTemplate(notificationRequest);
        }
    }

    /*
     * Look up the template priority in Redis; if not found, try the DB.
     * If still not found, assign medium priority (2).
     */
    private void assignPriorityWithTemplate(NotificationRequest notificationRequest) {
        String templateName = notificationRequest.getContent().getTemplateName();
        int priority = redisService.get(templateName);
        if (priority == -1) { // Not found in Redis, try DB.
            try {
                Template usedTemplate = templateRepository.findByName(templateName)
                        .orElseThrow(() -> new TemplateNotFoundException("Template with name: " + templateName + " not found"));

                priority = usedTemplate.getTemplatePriority();
                if (priority == 1 || priority == 2 || priority == 3) {
                    redisService.set(templateName, priority);
                }
            } catch (TemplateNotFoundException e) {
                log.error("{} For notificationRequest: {}", e.getMessage(), notificationRequest);
            } catch (Exception e) {
                log.error("Unexpected Exception: {} For notificationRequest: {}", e.getMessage(), notificationRequest);
            }
        }

        if (priority != 1 && priority != 2 && priority != 3) {
            notificationRequest.setNotificationPriority(2);
        } else {
            notificationRequest.setNotificationPriority(priority);
        }
    }

}
