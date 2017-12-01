package com.tongwii.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(TongWiiProperties jHipsterProperties) {
        TongWiiProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
//            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".devices", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".files", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".floors", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".logs", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".messages", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".messageComments", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".residences", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".rooms", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".userContacts", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.User.class.getName() + ".userRooms", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Role.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.MessageComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Message.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Message.class.getName() + ".messageComments", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.File.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.File.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.MessageType.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Residence.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Residence.class.getName() + ".messages", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Residence.class.getName() + ".floors", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Floor.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Floor.class.getName() + ".rooms", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Room.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Room.class.getName() + ".userRooms", jcacheConfiguration);
            cm.createCache(com.tongwii.domain.UserRoom.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Log.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.UserContact.class.getName(), jcacheConfiguration);
            cm.createCache(com.tongwii.domain.Device.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
