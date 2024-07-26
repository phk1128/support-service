package com.example.supportservice.controller;



import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Value("${server.env}")
    private String env;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.serverAddress}")
    private String serverAddress;
    @Value("${serverName}")
    private String serverName;


    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck() {

        HealthDto healthDto = HealthDto.builder()
                .env(env)
                .serverPort(serverPort)
                .serverAddress(serverAddress)
                .serverName(serverName)
                .build();

        return ResponseEntity.ok(healthDto);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {
        return ResponseEntity.ok(env);
    }

    @NoArgsConstructor
    @Data
    static class HealthDto {

        private String env;
        private String serverPort;
        private String serverAddress;
        private String serverName;


        @Builder
        public HealthDto(String env, String serverPort, String serverAddress, String serverName) {
            this.env = env;
            this.serverPort = serverPort;
            this.serverAddress = serverAddress;
            this.serverName = serverName;

        }



    }


}


