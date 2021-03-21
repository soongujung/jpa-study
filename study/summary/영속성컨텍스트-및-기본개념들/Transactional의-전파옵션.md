# Transactional의 전파옵션

Transaction 의 경쟁문제와 전파옵션은 티켓 쪽 업무를 담당하시는 분들이거나, 결제 등의 분야를 담당하시는 분들이 자주 접하는 문제인 듯 하다. 나의 경우는 아직까지는 결제분야 처럼 트랜잭션이 복잡한 분야에서 일한 적이 없다. 대신 조회 SQL이 복잡한 분야를 많이 겪어봤었다. 이렇게 결제 분야에 경험이 부족한 이유로 인해 아래에 정리 글들이 조금은 어색할 수도 있을것 같다는 생각이 든다.  



# 참고자료

- [자바 ORM 표준 JPA 프로그래밍](https://ridibooks.com/books/3984000009)
- [Transactional 정리 및 예제 - 갓대희의 작은 공간](https://goddaehee.tistory.com/167)
- [@Transactional](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html)
- [Isolation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Isolation.html)



개념 정리는 이번주 또는 다음주 중으로 정리 예정