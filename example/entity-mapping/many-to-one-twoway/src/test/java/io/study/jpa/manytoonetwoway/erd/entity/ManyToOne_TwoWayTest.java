package io.study.jpa.manytoonetwoway.erd.entity;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
class ManyToOne_TwoWayTest {

	@Autowired
	EntityManager em;

	@Test
	@DisplayName("인서트 테스트 #1")
	void testInsert1(){
		Department d1 = new Department("동작소방서");
		Employee e1 = new Employee("소방관1", d1);
		e1.assignDepartment(d1);

		em.persist(e1);
		em.persist(d1);

		em.flush();
	}
}
