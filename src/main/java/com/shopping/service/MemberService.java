package com.shopping.service;

import com.shopping.entity.Member;
import com.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor // final이나 @NotNull이 붙어 있는 변수에 생성자를 만들어 줍니다.
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository ;

    public Member saveMember(Member member){
        validateDuplicateMember(member) ;
        return memberRepository.save(member) ;
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()) ;

        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.") ;
        }
    }

    @Override // 이메일을 이용하여 회원 정보를 찾기
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email) ;
        if(member == null){ // 회원이 존재하지 않는 경우
            throw new UsernameNotFoundException(email) ;
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}


