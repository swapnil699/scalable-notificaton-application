package org.swapnil.scalablenotificaton.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String templateName, int templatePriority) {
        try {
            redisTemplate.opsForValue().set(templateName, Integer.toString(templatePriority), 1, TimeUnit.DAYS); // Refresh Daily
        } catch (Exception exception) {
            log.error("Exception setting value to Redis. Exception: {}", exception.getMessage());
        }
    }

    public int get(String templateName) {
        try {
            String value = redisTemplate.opsForValue().get(templateName);
            if (value == null) {
                log.info("{} template not available in Redis", templateName);
                return -1;
            }
            return Integer.parseInt(value);
        } catch (Exception exception) {
            log.error("Exception getting value from Redis. Exception: {}", exception.getMessage());
            return -1;
        }
    }
}
