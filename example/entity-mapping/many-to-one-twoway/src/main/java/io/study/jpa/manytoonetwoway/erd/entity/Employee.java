package io.study.jpa.manytoonetwoway.erd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYEE")
@Getter @Setter
@NoArgsConstructor
public class Employee {

	@Id @GeneratedValue
	@Column(name = "EMP_NO")
	private Long empNo;

	@Column(name = "EMP_NAME")
	private String empName;

	@ManyToOne
	@JoinColumn(name = "DEPT_NO")
	private Department department;

	public Employee(String empName){
		this.empName = empName;
	}

	public Employee(String empName, Department department){
		this.empName = empName;
		this.department = department;
	}

	/**
	 * 헬퍼함수 (편의제공 메서드)
	 * @param department
	 */
	public void assignDepartment(Department department){
		this.department = department;
		department.getEmployees().add(this);
	}
}
