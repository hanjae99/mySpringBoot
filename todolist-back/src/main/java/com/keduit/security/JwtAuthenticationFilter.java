package com.keduit.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            log.info("filter is running");
            if (token != null && !token.equalsIgnoreCase("null")){
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("userid: " + userId);

                AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userId, null, AuthorityUtils.NO_AUTHORITIES
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(auth);
                SecurityContextHolder.setContext(securityContext);
            }
        }catch (Exception e){
            logger.error("사용자 인증 컨텍스트 생성 에러", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }

        return null;
    }
}
