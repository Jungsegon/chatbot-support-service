package com.example.chatservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityCheckUtil {
    public static void checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.error("SecurityContextHolder: 인증 정보 없음");
        } else {
            log.info("SecurityContextHolder: 현재 인증된 사용자 - {}", authentication.getName());
        }
    }
}
