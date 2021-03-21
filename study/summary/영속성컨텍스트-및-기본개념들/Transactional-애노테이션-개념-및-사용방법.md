# @Transactional 의 개념 및 사용방법

# 참고자료

- [자바 ORM 표준 JPA 프로그래밍](https://ridibooks.com/books/3984000009)
- [Transactional 정리 및 예제](https://goddaehee.tistory.com/167)



# 1. @Transactional

> 참고자료 : [Transactional 정리 및 예제 - 갓대희의 작은 공간](https://goddaehee.tistory.com/167)



## 기본 개념

- 스프링은 트랜잭션을 선언적인 방식으로 편하게 처리할 수 있도록 @Transactional 애노테이션을 지원한다. 클래스 또는 메서드에 @Transactional 애노테이션을 추가하는 방식으로 트랜잭션을 클래스/메서드 단위로 지정할 수 있다.  

- 클래스, 메서드 위에 @Trasactional 이 추가되면 해당 클래스/메서드에는 스프링의 트랜잭션 제어 기능이 적용된 프록시 객체가 생성된다.  
- 정상/익셉션 발생 여부 등에 따라 Commit/Rollback이 수행된다.



# 2. @Transactional 설정

아래의 예제는 어떤 Datasource 에 대해 @Transactional 이 사용가능하도록 지정할지 설정하는 단순 예제이다.

```java
@EnableTransactionManagement
public class MysqlDatasourceConfig {
  // ...
  @Bean
  public PlatformTransactionManager transactionManager() throws Exception{
    return new DataSourceTransactionManager(dataSource());
  }
}
```



# 3. @Transactional 사용 단순 예제

```java
@Transactional
public boolean insertEmployee(Employee employee){
  // ...
}
```

















