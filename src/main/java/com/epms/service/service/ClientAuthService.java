package com.epms.service.service;

import com.epms.service.entity.ApiClient;
import com.epms.service.repository.ApiClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientAuthService {
    
    private final ApiClientRepository apiClientRepository;
    
    public boolean validateClient(String clientId, String clientSecret) {
        try {
            Optional<ApiClient> clientOpt = apiClientRepository.findByClientIdAndIsActive(clientId, true);
            
            return clientOpt
                .filter(client -> client.getClientSecret().equals(clientSecret))
                .isPresent();
                
        } catch (Exception e) {
            log.error("Error validating client credentials for clientId: {}", clientId, e);
            return false;
        }
    }
}