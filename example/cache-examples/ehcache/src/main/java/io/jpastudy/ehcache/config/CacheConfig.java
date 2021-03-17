package io.jpastudy.ehcache.config;

import io.jpastudy.ehcache.web.CustomerDto;
import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

	public static final String DB_CACHE = "db_cache";
	public static final String USER_CACHE = "user_cache";

	private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

	public CacheConfig(){
		ResourcePoolsBuilder memoryPoolBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10000, EntryUnit.ENTRIES);

		CacheConfigurationBuilder<Object, Object> cacheBuilder = CacheConfigurationBuilder
			.newCacheConfigurationBuilder(Object.class, Object.class, memoryPoolBuilder);

		cacheBuilder
			.withSizeOfMaxObjectSize(1000, MemoryUnit.B)
			.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(300)))
			.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(600)));

		this.jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheBuilder);
	}

	@Bean
	public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager){
		return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
	}

	@Bean
	public JCacheManagerCustomizer cacheManagerCustomizer(){

		ResourcePoolsBuilder memoryPoolBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10000, EntryUnit.ENTRIES);

		CacheConfigurationBuilder<Long, CustomerDto> cacheConfigBuilder = CacheConfigurationBuilder
			.newCacheConfigurationBuilder(Long.class, CustomerDto.class, memoryPoolBuilder);

		cacheConfigBuilder
			.withSizeOfMaxObjectSize(1000, MemoryUnit.B)
			.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10)))
			.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(20)));

		return cacheManager -> {
			cacheManager.createCache(DB_CACHE, jcacheConfiguration);
			cacheManager.createCache(USER_CACHE, Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfigBuilder));
		};
	}
}
