package io.jpastudy.ehcache.web;

import io.jpastudy.ehcache.config.CacheConfig;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity @Table
@Getter
@Cacheable
@Cache(region = CacheConfig.DB_CACHE, usage = CacheConcurrencyStrategy.READ_ONLY)
public class Customer {

	@Id @GeneratedValue
	@Column(name = "id")
	private Long customerId;

	@Column(name = "name")
	private String customerName;
}
