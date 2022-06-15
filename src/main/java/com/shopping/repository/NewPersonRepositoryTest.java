package com.shopping.repository;

import com.shopping.entity.NewPerson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NewPersonRepositoryTest {
    @Autowired
    NewPersonRepository newPersonRepository;

    @Test
    @DisplayName("NewPerson 저장 테스트")
    public void createItemTest(){
        NewPerson bean = new NewPerson() ;
        bean.setName("이순신");
        bean.setId("lee");
        bean.setAddress("용산구 이태원동");
        bean.setSalary(12345);

        NewPerson savedItem = newPersonRepository.save(bean) ;
        System.out.println(savedItem.toString());

        Long cnt = newPersonRepository.count() ;
        System.out.println("엔터티 개수 : " + cnt);
    }
}
