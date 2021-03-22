# Transactional 경쟁 문제와 격리성보장

Transaction 의 경쟁문제와 전파옵션은 티켓 쪽 업무를 담당하시는 분들이거나, 결제 등의 분야를 담당하시는 분들이 자주 접하는 문제인 듯 하다. 나의 경우는 아직까지는 결제분야 처럼 트랜잭션이 복잡한 분야에서 일한 적이 없다. 대신 조회 SQL이 복잡한 분야를 많이 겪어봤었다. 이렇게 결제 분야에 경험이 부족한 이유로 인해 아래에 정리 글들이 조금은 어색할 수도 있을것 같다는 생각이 든다.  

  

# 참고자료

- [자바 ORM 표준 JPA 프로그래밍](https://ridibooks.com/books/3984000009)
- [Transactional 정리 및 예제 - 갓대희의 작은 공간](https://goddaehee.tistory.com/167)
- [@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html)
- [Isolation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Isolation.html)



# 1. 트랜잭션의 4가지 원칙

> 참고자료 : [자바 ORM 표준 JPA 프로그래밍](https://ridibooks.com/books/3984000009)

트랜잭션은 4가지 원칙을 만족해야한다. 이 원칙은 ACID 라고 부른다. (ACID - Atomicity, Consistency, Isolation, Duability, 참고 - [ACID](https://en.wikipedia.org/wiki/ACID))

- 원자성(Atomicity)
  - 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공하든가 모두 실패해야 한다.
- 일관성(Consistency)
  - 모든 트랜잭션은 일관성 있는 데이터베이스 상태를 유지해야 한다.
  - 예를 들어 데이터베이스에서 정한 무결성 제약 조건을 만족해야 한다.
- **격리성(Isolation)**
  - 동시에 실행되는 트랜잭션 들이 서로에게 영향을 미치지 않도록 격리한다.
  - 예를 들어 동시에 같은 데이터를 수정하지 못하도록 해야 한다.
  - 격리성은 동시성과 관련된 성능 이슈로 인해 격리 수준을 선택할 수 있다.
- 지속성(Durability)
  - 트랜잭션을 성공적으로 끝내면 그 결과가 항상 기록되어야 한다.
  - 중간에 시스템에 문제가 발생하더라도 데이터베이스 로그 등을 사용해 성공한 트랜잭션 내용을 복구해야 한다.



위의 네가지 원칙들 중 트랜잭션의 원자성, 일관성, 지속성은 보장하는 것이 가능하다. 다만 이 원칙 들 중에서 **격리성(Isolation)은 완벽하게 보장하려면 성능에 어느정도의 저하가 발생한다.** 격리성을 완벽하게 보장하려면 **트랜잭션을 거의 차례대로 수행해야 하기 때문**이다.



# 2. 트랜잭션의 격리성 보장의 어려움

**격리성(Isolation)은 완벽하게 보장하려면 성능에 어느정도의 저하가 발생한다.** 격리성을 완벽하게 보장하려면 **트랜잭션을 거의 차례대로 수행해야 하기 때문**이다. 이런 격리성에 대해서 ANSI 에서는 4단계로 격리의 수준을 정의했다.



## 트랜잭션 격리수준 4단계

- **READ UNCOMMITED**
  - **"커밋하지 않은 데이터를 읽을 수 있다."**
  - Dirty Read 를 허용하는 격리수준을 Read Uncommitted 라고 한다. (이 외에도 Non-Repeated Read, Phantom Read 역시 발생한다.)
  - 문제점) Dirty Read, Non-Repeated Read, Phantom Read 발생
- **READ COMMITED**
  - **"커밋한 데이터만 읽을 수 있다."** (따라서 Dirty Read 는 발생하지 않는다)
  - Dirty Read 를 허용하지 않지만, Non Repeatable Read 를 허용하는 격리수준을 Read Commited 라고 한다.
  - 문제점) Non-Repeated Read, Phantom Read 발생
- **REPEATABLE READ**
  - **"한번 조회한 데이터를 반복해서 조회해도 같은 데이터가 조회된다."**
  - Non Repeatable Read 는 허용하지 않지만, Phantom Read 는 허용하는 격리수준을 Repeatable Read 라 한다.
  - 문제점) Phantom Read 발생
- **SERIALIZABLE**
  - **"가장 엄격한 트랜잭션 관리 수준"**
  - Phantom Read 가 발생하지 않는다.
  - 문제점) 동시성 처리 성능이 급격히 떨어질 수도 있다.



# 3. (용어정리) Dirty Read, Repeatable Read, Non Repeatable Read

위에서 정리한 트랜잭션의 4가지 개념에서는 Dirty Read, Repeatable Read, Non Repeatable Read 라는 용어가 나오는데, 이 용어들을 여기서 정리해보려 한다.

- **DIRTY READ**
  - 트랜잭션 t1이 데이터를 수정하고 있을 때
  - 트랜잭션 t2가 t1이 수정중인 데이터를 읽을 수 있다.
  - t2 가 DIRTY READ 한 데이터를 사용하고 있는 중에 t1을 롤백하면 데이터 정합성에 문제가 생긴다.
- **NON REPEATABLE READ** : 다른 트랜잭션이 수정한 데이터로 읽어들여 조회하는 것
  - 트랜잭션 t1이 회원 A를 조회 중인데,
  - 트랜잭션 t2가 회원 A를 수정 & 커밋 하면
  - 트랜잭션 t1이 다시 회원 A를 조회했을 때 t2가 수정한 데이터가 조회된다.
- 이렇게 반복해서 같은 데이터를 읽을 수 없는 상태를 **Non Repeatable Read** 라고 한다.
- **REAPEATABLE READ**
  - 한번 조회한 데이터를 반복해서 조회해도 같은 데이터가 조회된다.
- **PHANTOM READ** : 다른 트랜잭션이 추가한 데이터가 하나 더 추가되어 조회되는 것
  - 트랜잭션 t1이 10살 이하의 회원을 조회했는데
  - 트랜잭션 t2가 5살 회원을 추가하고 커밋하면
  - 트랜잭션 t1이 다시 10살 이하의 회원을 조회했을 때 회원 하나가 추가된 상태로 조회된다.
  - 반복 조회시 결과집합이 달라지는 것을 **Phantom Read** 라고 한다.



# 4. @Transactional 에 격리수준 지정

> 참고자료
>
> - [@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html)
> - [Isolation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Isolation.html)

스프링에서 지원하는 선언적 트랜잭션 기능인 @Transactional 에서는 격리수준을 지정할 수 있다. 

ex) 

```java
@Transactional(isolation = Isolation.DEFAULT)
public void buyTicket(Ticket ticket){
  // ..
}
```

  

위 예제에서 보이듯이 [@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html) 애노테이션에 격리수준을 지정할 때 Transactional 애노테이션 내의 isolation 필드에 격리수준 옵션을 지정해주는 것으로 격리수준 옵션을 지정할 수 있다. **isolation** 필드에 **격리 수준 레벨**로 지정하는 [Isolation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Isolation.html) 이라는 enum 에는 아래와 같은 상수들이 있다.



- DEFAULT
  - Use the default isolation level of the underlying datastore.
  - 기본 격리수준을 따르고, Database의 격리수준을 그대로 따라간다.
- READ_COMMITTED
  - A constant indicating that dirty reads are prevented; non-repeatable reads and phantom reads can occur.
  - **"커밋한 데이터만 읽을 수 있다."**
  - Dirty Read 가 방지됨을 나타내는 상수이다. 참고로 Non-Repeatable Read, Phantom Read 가 발생할 가능성이 있다.
- READ_UNCOMMITED
  - A constant indicating that dirty reads, non-repeatable reads and phantom reads can occur.
  - **"커밋하지 않은 데이터를 읽을 수 있다"**  
  - Dirty Read, Non-Repeatable Read, Phantom Read 가 발생할 수 있음을 나타내는 상수이다.
  - Dirty Read를 허용하는 격리수준을 Read Uncommitted 라고 한다.(위에서 정리)
  - Dirty Read 외에도 Non-Repeated Read, Phantom Read 역시 발생할 가능성이 있다.
  - 문제점) Non-Repeable Read, Phantom Read 가 발생한다.
- REPEATABLE_READ
  - A constant indicating that dirty reads and non-repeatable reads are prevented; phantom reads can occur.
  - **"한번 조회한 데이터를 반복해서 조회해도 같은 데이터가 조회된다."**
  - Dirty Read, Non-Repeatable Read 가 방지됨을 나타내는 상수이다. Phantom Read 현상이 발생할 수 있는 가능성이 존재한다.
  - Non Repeatable Read 는 허용하지 않지만, Phantom Read 는 허용하는 격리수준을 Repeatable Read 라 한다.
  - 문제점) Phantom Read 발생
- SERIALIZABLE
  - A constant indicating that dirty reads, non-repeatable reads and phantom reads are prevented.
  - **"가장 엄격한 트랜잭션 관리 수준"**
  - Dirty Read, Non-Repeatable Read, Phantom Read 가 방지되는 것을 나타내는 상수이다.
  - Phantom Read 가 발생하지 않는다.
  - 문제점) 동시성 처리 성능이 급격히 떨어질 수 있다.









