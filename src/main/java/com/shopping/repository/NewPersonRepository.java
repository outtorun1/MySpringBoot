package com.shopping.repository;

import com.shopping.entity.NewPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewPersonRepository
        extends JpaRepository<NewPerson, String>, QuerydslPredicateExecutor<NewPerson> {
    List<NewPerson> findByOrderByNameAsc();
    List<NewPerson> findByAddressEquals(String address); // CLOb 데이터 문제
    List<NewPerson> findByOrderBySalaryDesc() ;

    @Query(" select i from NewPerson i where i.salary >= :salary " +
            "   ")
    List<NewPerson> findBySalary(@Param("salary") int salary) ;
}