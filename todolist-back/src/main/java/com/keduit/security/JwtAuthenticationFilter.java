package com.keduit.security;

import antlr.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearToken(request);
            logger.info("Filter is running...");

            if(token != null && !token.equalsIgnoreCase("null")) {
                    String userId = tokenProvider.validateAndGetUserId(token);
                log.info("userId : " + userId);
                logger.info("userId..." + userId);

                AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken (
                        userId,
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );

                // securityContext에 사용자 인증 정보 저장
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(auth);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            logger.info("사용자 인증 컨텍스트 생성 에러", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7); // 7번지부터 끝까지
        }

        return null;
    }


}
