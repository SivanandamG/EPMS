package com.epms.service.aop;

import com.epms.service.exception.UnauthorizedException;
import com.epms.service.service.ClientAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityAspect {
    
    private final ClientAuthService clientAuthService;
    
    @Around("@annotation(secureApi)")
    public Object validateClientCredentials(ProceedingJoinPoint joinPoint, SecureApi secureApi) throws Throwable {
        if (!secureApi.requireClientAuth()) {
            return joinPoint.proceed();
        }
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        String clientId = request.getHeader("X-Client-Id");
        String clientSecret = request.getHeader("X-Client-Secret");
        
        if (clientId == null || clientSecret == null) {
            log.warn("Missing client credentials in request");
            throw new UnauthorizedException("Client credentials required");
        }
        
        if (!clientAuthService.validateClient(clientId, clientSecret)) {
            log.warn("Invalid client credentials: {}", clientId);
            throw new UnauthorizedException("Invalid client credentials");
        }
        
        log.info("Client authenticated successfully: {}", clientId);
        return joinPoint.proceed();
    }
}