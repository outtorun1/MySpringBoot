package com.shopping.entity;

import com.shopping.dto.MemberFormDto;
import com.shopping.repository.CartRepository;
import com.shopping.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


/*
    멤버 객체를 생성합니다.
*/
@SpringBootTest
@Transactional
public class CartTest {
    @Autowired
    PasswordEncoder passwordEncoder ;

    private Member createMember(){ // 멤버 객체 생성
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setAddress("서울시 마포구 공덕동");
        memberFormDto.setName("김현식");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder) ;
    }

    @Autowired
    MemberRepository memberRepository ;

    @Autowired
    CartRepository cartRepository ;

    @Test
    @DisplayName("장바구니 회원 엔터티 매핑 조회")
    public void findCartAndMemberTest(){
        Member member = createMember() ;
        memberRepository.save(member) ;

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart) ;
        
        Cart savedCart = cartRepository.findById(cart.getId())
                            .orElseThrow(EntityNotFoundException::new);
        
        this.ShowCartInfo(savedCart) ;

        Assertions.assertEquals(savedCart.getMember().getId(), member.getId());
    }

    private void ShowCartInfo(Cart savedCart) {
        System.out.println("카트 아이디 : " + savedCart.getId());
        System.out.println("아이디 : " + savedCart.getMember().getId());
        System.out.println("이름 : " + savedCart.getMember().getName());
        System.out.println("이메일 : " + savedCart.getMember().getEmail());
        System.out.println("주소 : " + savedCart.getMember().getAddress());
        System.out.println("비번 : " + savedCart.getMember().getPassword());
        System.out.println("role : " + savedCart.getMember().getRole());
    }
}
