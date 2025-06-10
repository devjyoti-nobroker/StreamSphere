package com.nobroker.streamSphere.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public <T> T get(String key, TypeReference<T> typeReference) {
        try {
            String cached = redisTemplate.opsForValue().get(key);
            if (cached == null) return null;

            return objectMapper.readValue(cached, typeReference);
        } catch (Exception e) {
            log.error("Redis get failed", e);
            return null;
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            String cached = redisTemplate.opsForValue().get(key);
            if (cached == null) return null;

            return objectMapper.readValue(cached, clazz);
        } catch (Exception e) {
            log.error("Redis get failed", e);
            return null;
        }
    }

    public void set(String key, Object value, Long ttlInSeconds) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, ttlInSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Failed to serialize and store object in Redis", e);
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
