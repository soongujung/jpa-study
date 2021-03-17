package io.jpastudy.ehcache.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CustomerController {

	private final CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService){
		this.customerService = customerService;
	}

	@GetMapping("/customers/{id}")
	@ResponseBody
	public CustomerDto getCustomer(@PathVariable Long id){
		log.info("======= Controller");
		return customerService.getCustomer(id);
	}
}
