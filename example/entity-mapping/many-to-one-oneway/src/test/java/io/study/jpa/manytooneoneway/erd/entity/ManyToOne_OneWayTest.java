package io.study.jpa.manytooneoneway.erd.entity;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class ManyToOne_OneWayTest {

	@Autowired
	EntityManager em;

	@BeforeEach
	void init(){

	}

	@Test
	@DisplayName("Employee, Department 양방향 모두 세팅해서 테스트")
	void testInsert(){
		Employee e1 = new Employee("찰리");
		Department d1 = new Department("슈퍼개미");

		em.persist(e1);
		em.persist(d1);
		em.flush();
	}

	@Test
	void testInsert2(){
		Employee e1 = new Employee("경찰관1");
		em.persist(e1);
		em.flush();
	}

}
