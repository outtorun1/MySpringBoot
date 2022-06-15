package com.shopping.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopping.entity.NewPerson;
import com.shopping.entity.QNewPerson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
public class NewPersonRepositoryTest02 {
    @Autowired
    NewPersonRepository newPersonRepository;

    @Test
    @DisplayName("회원 여러명 생성하기")
    void createNewPersonTest(){
        String[] address = {"용산", "마포", "서대문", "금천"};
        String[] name = {"김철수", "이민수", "박홍기"};
        for (int i = 1; i <=10 ; i++) {
            NewPerson person = new NewPerson();
            person.setId("person" + i);
            person.setName(name[i % name.length]);
            person.setAddress(address[i % address.length]);
            person.setSalary(100*i);
            NewPerson savedData = newPersonRepository.save(person);
            System.out.println(savedData.toString()) ;
        }
    }


    @Test
    @DisplayName("이름순 정렬 테스트")
    public void findByOrderByNameAsc(){
        List<NewPerson> lists = newPersonRepository.findByOrderByNameAsc();
        for(NewPerson person : lists){
            System.out.println(person.toString()) ;
        }
    }

    /*
2022-05-27 12:09:37.023 TRACE 9996 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [CLOB] - [마포]
2022-05-27 12:09:37.027  WARN 9996 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 932, SQLState: 42000
2022-05-27 12:09:37.028 ERROR 9996 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : ORA-00932: inconsistent datatypes: expected - got CLOB*/

    @Test
    @DisplayName("마포인 사람 조회")
    public void findByAddressEquals(){
        List<NewPerson> lists = newPersonRepository.findByAddressEquals("마포");
        for(NewPerson person : lists){
            System.out.println(person.toString()) ;
        }
    }

    @Test
    @DisplayName("고소득자 우선 정렬")
    public void findByOrderBySalaryDesc() {
        List<NewPerson> lists = newPersonRepository.findByOrderBySalaryDesc();
        for(NewPerson person : lists){
            System.out.println(person.toString()) ;
        }
    }

    @Test
    @DisplayName("@Query를 사용한 고액 연봉자 조회")
    public void findBySalary(){
        List<NewPerson> newPersonList = newPersonRepository.findBySalary(500);
        for(NewPerson newPerson : newPersonList){
            System.out.println(newPerson.toString()) ;
        }
    }

    @PersistenceContext // JPA가 동작하는 영속성 작업 구간
    EntityManager em ; // 엔터티 관리자

    @Test
    @DisplayName("newperson query Dsl Test01")
    public void newPersonDslTest01(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em) ;
        QNewPerson qNewPerson = QNewPerson.newPerson ;
        JPAQuery<NewPerson> query = queryFactory
                .selectFrom(qNewPerson)
                .where(qNewPerson.salary.lt(700))
                .where(qNewPerson.address.like("%" + "포" + "%"))
                .orderBy(qNewPerson.name.desc()) ;

        List<NewPerson> newPersonList = query.fetch() ;
        for(NewPerson bean : newPersonList){
            System.out.println(bean.toString()) ;
        }
    }

    @Test
    @DisplayName("create newperson data")
    public void createNewPersonData(){
        String[] address = {"용산", "마포", "서대문", "금천"};
        int[] salary = {111, 222, 333, 444, 555};

        for (int i = 1; i <= 30 ; i++) {
            NewPerson newPerson = new NewPerson();

            newPerson.setId("myid" + i);
            newPerson.setSalary(salary[i%salary.length]);
            newPerson.setAddress(address[i%address.length]);
            newPerson.setName("김철수" + (i*i));

            newPersonRepository.save(newPerson);
        }
    }

    @Test
    @DisplayName("newperson query Dsl Test02")
    public void newPersonDslTest02(){
        String myaddress = "포" ;
        int salary = 400 ;

        QNewPerson newPerson = QNewPerson.newPerson ;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(newPerson.address.like("%" + myaddress + "%")) ;
        booleanBuilder.and(newPerson.salary.lt(salary)) ;

        // Sort.by("name").ascending(), Sort.by("name").descending()
        Pageable pageable = PageRequest.of(1, 3, Sort.by("name").ascending()) ;

        Page<NewPerson> newPersonPagingResult
                = newPersonRepository.findAll(booleanBuilder, pageable) ;
        System.out.println("total elements : " + newPersonPagingResult.getTotalElements());

        List<NewPerson> resultNewPersonList = newPersonPagingResult.getContent() ;
        for(NewPerson resultNewPerson : resultNewPersonList){
            System.out.println(resultNewPerson.toString());
        }
    }
}
