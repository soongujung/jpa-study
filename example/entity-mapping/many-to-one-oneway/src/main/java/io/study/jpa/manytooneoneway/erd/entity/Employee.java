package io.study.jpa.manytooneoneway.erd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYEE")
@Getter @Setter
@RequiredArgsConstructor
public class Employee {

	@Id @GeneratedValue
	@Column(name = "EMP_NO")
	private Long empNo;

	@Column(name = "EMP_NAME")
	private String empName;

	@ManyToOne
	@JoinColumn(name = "DEPT_NO")
	private Department dept;

	public Employee(String empName){
		this.empName = empName;
	}

	public Employee(String empName, Department dept){
		this.empName = empName;
		this.dept = dept;
	}

	/**
	 * 헬퍼 함수
	 * @param dept
	 */
	public void assignDepartment(Department dept){
		this.setDept(dept);
	}
}
