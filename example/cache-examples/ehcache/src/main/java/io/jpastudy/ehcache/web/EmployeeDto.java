package io.jpastudy.ehcache.web;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class EmployeeDto {

	private String employeeName;

	public static EmployeeDto of(Employee employee){
		final String employeeName = employee.getName();

		return EmployeeDto.builder()
			.employeeName(employeeName)
			.build();
	}
}
