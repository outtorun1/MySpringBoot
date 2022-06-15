package com.shopping.service;

import com.shopping.dto.MemberFormDto;
import com.shopping.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 테스트가 완료 되고 나면, 자동으로 롤백이 됩니다.
public class MemberServiceTest {
    @Autowired
    MemberService memberService ;

    @Autowired(required = false)
    PasswordEncoder passwordEncoder ;

    // 우리가 form에서 입력하여 넣은 데이터라고 보면 됩니다.
    private Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder) ;
    }

    @Test // 해당 메소드를 테스트용 메소드로 사용하겠습니다.
    @DisplayName("회원 가입 테스트")
    public void saveMember(){
        Member member = createMember(); // 사용자가 기입한 값

        // 실제로 jpa를 통하여 데이터 베이스에 저장된 값
        Member savedMember = memberService.saveMember(member) ;

        // assertEquals(기입한값, 저장된값) : 두 개의 값에 대한 비교를 수행합니다.
        Assertions.assertEquals(member.getEmail(), savedMember.getEmail());
        Assertions.assertEquals(member.getName(), savedMember.getName());
        Assertions.assertEquals(member.getAddress(), savedMember.getAddress());
        Assertions.assertEquals(member.getPassword(), savedMember.getPassword());
        Assertions.assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateTest(){
        // 이미 저장되어 있는 회원
        Member member1 = createMember() ;

        // 이번에 저장할 회원 정보
        Member member2 = createMember() ;

        memberService.saveMember(member1) ; // 1번 회원 저장

        Throwable err = Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2) ; // 동일한 이메일을 가지고 2번 회원 저장하기
        }) ;

        System.out.println("예외 메시지 검증하기");
        Assertions.assertEquals("이미 가입된 회원입니다.", err.getMessage());
        err.printStackTrace();
    }
}