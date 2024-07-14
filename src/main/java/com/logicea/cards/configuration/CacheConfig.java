package com.logicea.cards.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class CacheConfig implements CachingConfigurer {

    public static final String CARD_CACHE_KEY = "cardCache";

    @Override
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(
                caffeineCache(CARD_CACHE_KEY, Duration.ofMinutes(5))
        ));

        return CachingConfigurer.super.cacheManager();
    }

    private CaffeineCache caffeineCache(String cacheKey, Duration duration) {
        return new CaffeineCache(
                cacheKey,
                Caffeine.newBuilder()
                .expireAfterWrite(duration)
                .build());
    }

    void evictCache() {
        Objects.requireNonNull(Objects.requireNonNull(cacheManager()).getCache(CARD_CACHE_KEY)).clear();
    }
}
