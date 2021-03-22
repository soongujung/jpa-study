package io.study.jpa.manytooneoneway.erd.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DEPARTMENT")
@Getter @Setter
public class Department {

	@Id @GeneratedValue
	@Column(name = "DEPT_NO")
	Long id;

	@Column(name = "DEPT_NAME")
	private String deptName;

	public Department(String deptName){
		this.deptName = deptName;
	}

}
