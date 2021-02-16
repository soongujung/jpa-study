package io.study.jpa.manytoonetwoway.erd.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "DEPARTMENT")
@Entity
@Getter @Setter
@NoArgsConstructor
public class Department {

	@Id @GeneratedValue
	@Column(name = "DEPT_NO")
	private Long deptNo;

	@Column(name = "DEPT_NAME")
	private String deptName;

	public Department(String deptName){
		this.deptName = deptName;
	}

	@OneToMany(mappedBy = "department")
	List<Employee> employees = new ArrayList<>();

	public void addMember(Employee employee){
		if(employee.getDepartment() != this){
			employee.setDepartment(this);
		}

		if(!employees.contains(employee)){
			this.employees.add(employee);
		}
	}
}
