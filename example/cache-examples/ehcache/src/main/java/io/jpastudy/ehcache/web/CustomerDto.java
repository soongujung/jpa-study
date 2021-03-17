package io.jpastudy.ehcache.web;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CustomerDto {

	private String customerName;

	public static CustomerDto of(Customer customer){
		final String customerName = customer.getCustomerName();

		return CustomerDto.builder()
			.customerName(customerName)
			.build();
	}
}
