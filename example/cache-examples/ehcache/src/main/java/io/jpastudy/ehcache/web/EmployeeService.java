package io.jpastudy.ehcache.web;

import io.jpastudy.ehcache.config.CacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Cacheable(value = CacheConfig.USER_CACHE, key="#id")
	public EmployeeDto findEmployee(Long id){
		log.info("======= EmployeeService > findEmployee(" + id + ")");
		log.info("=======>> \tEmployeeService > before repository call");
		Employee employee = repository.findById(id).orElseGet(Employee::new);
		log.info("=======>> \tEmployeeService > after repository call");
		return EmployeeDto.of(employee);
	}
}
