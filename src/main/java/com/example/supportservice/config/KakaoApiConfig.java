package com.example.supportservice.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kakao.api")
@Data
public class KakaoApiConfig {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
    private String tokenUrl;
    private String userinfoUrl;
}
