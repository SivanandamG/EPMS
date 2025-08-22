package com.epms.service.aop;

import com.epms.service.exception.UnauthorizedException;
import com.epms.service.service.ClientAuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityAspectTest {

    @Mock
    private ClientAuthService clientAuthService;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private SecurityAspect securityAspect;

    private MockHttpServletRequest request;
    private SecureApi secureApiAnnotation;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        secureApiAnnotation = mock(SecureApi.class);
        when(secureApiAnnotation.requireClientAuth()).thenReturn(true);
    }

    @Test
    void validateClientCredentials_ValidCredentials_ProceedsSuccessfully() throws Throwable {
        // Given
        request.addHeader("X-Client-Id", "valid-client");
        request.addHeader("X-Client-Secret", "valid-secret");
        when(clientAuthService.validateClient("valid-client", "valid-secret")).thenReturn(true);
        when(joinPoint.proceed()).thenReturn("success");

        // When
        Object result = securityAspect.validateClientCredentials(joinPoint, secureApiAnnotation);

        // Then
        assertEquals("success", result);
        verify(joinPoint).proceed();
    }

    @Test
    void validateClientCredentials_MissingHeaders_ThrowsUnauthorizedException() {
        // When & Then
        assertThrows(UnauthorizedException.class, () -> 
            securityAspect.validateClientCredentials(joinPoint, secureApiAnnotation));
    }

    @Test
    void validateClientCredentials_InvalidCredentials_ThrowsUnauthorizedException() {
        // Given
        request.addHeader("X-Client-Id", "invalid-client");
        request.addHeader("X-Client-Secret", "invalid-secret");
        when(clientAuthService.validateClient("invalid-client", "invalid-secret")).thenReturn(false);

        // When & Then
        assertThrows(UnauthorizedException.class, () -> 
            securityAspect.validateClientCredentials(joinPoint, secureApiAnnotation));
    }

    @Test
    void validateClientCredentials_AuthNotRequired_ProceedsWithoutValidation() throws Throwable {
        // Given
        when(secureApiAnnotation.requireClientAuth()).thenReturn(false);
        when(joinPoint.proceed()).thenReturn("success");

        // When
        Object result = securityAspect.validateClientCredentials(joinPoint, secureApiAnnotation);

        // Then
        assertEquals("success", result);
        verify(joinPoint).proceed();
        verifyNoInteractions(clientAuthService);
    }
}