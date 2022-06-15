package com.shopping.repository;

import com.shopping.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일을 이용하여 회원 검색, 가입시 중복 체크
    Member findByEmail(String email) ;
}
