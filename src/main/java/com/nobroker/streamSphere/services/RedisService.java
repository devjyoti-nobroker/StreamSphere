package com.nobroker.streamSphere.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisService {

    private final Long RECENTLY_WATCHED_SIZE = 5L;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void addMovie(Long profileId, Long movieId){
        ListOperations<String,String> redisList = redisTemplate.opsForList();

        String key = String.valueOf(profileId);
        String value = String.valueOf(movieId);

        redisList.remove(key,1,value);
        if(redisList.size(key) != null && Objects.equals(redisList.size(key), RECENTLY_WATCHED_SIZE)){
            redisList.rightPop(key);
        }
        redisList.leftPush(key,value);
    }

    public List<Long> getMovies(Long profileId){
        ListOperations<String,String> redisList = redisTemplate.opsForList();
        String key = String.valueOf(profileId);
        return Objects.requireNonNull(
                redisList
                    .range(key, 0, RECENTLY_WATCHED_SIZE))
                    .stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList()
                    );
    }



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
