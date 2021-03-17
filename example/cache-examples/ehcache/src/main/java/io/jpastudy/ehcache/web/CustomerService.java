package io.jpastudy.ehcache.web;

import io.jpastudy.ehcache.config.CacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Cacheable(value = CacheConfig.USER_CACHE, key="#id")
	public CustomerDto getCustomer(Long id){
		log.info("======= CustomerService > getCustomer(" + id + ")");
		log.info("=======>> \tCustomerService > before repository call");
		Customer customer = repository.findById(id).orElseGet(Customer::new);
		log.info("=======>> \tCustomerService > after repository call");
		return CustomerDto.of(customer);
	}
}
