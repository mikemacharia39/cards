package com.logicea.cards.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
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
}
