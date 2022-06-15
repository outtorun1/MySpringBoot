package com.shopping.entity;

import com.shopping.constant.Role;
import com.shopping.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter @ToString
public class Member extends BaseEntity{
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String name ;

    @Column(unique = true)
    private String email ;

    private String password ;

    private String address ;

    @Enumerated(EnumType.STRING)
    private Role role ; // 일반인, 관리자 구분

    // 화면에서 넘어 오는 dto 객체와 비번을 암호화 해주는 객체를 사용하여 Member Entity 객체 생성하기
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        String password = passwordEncoder.encode(memberFormDto.getPassword()) ;
        member.setPassword(password);
        member.setRole(Role.USER); // 일반 사용자
        // member.setRole(Role.ADMIN); // 관리자

        return member ;
    }
}
