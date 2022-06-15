package com.shopping.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// AuditorAware : Spring JPA에서 제공해주는 감사(audit) 정보를 캡쳐해주는 인터페이스
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
        String userId = ""  ; // 작업자 정보
        if(authentication != null){
            userId = authentication.getName() ;
        }
        return Optional.of(userId) ;
    }
}
