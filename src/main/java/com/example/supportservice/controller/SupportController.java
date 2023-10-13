package com.example.supportservice.controller;


import com.example.supportservice.dto.SupportDto;
import com.example.supportservice.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/donation")
    public ResponseEntity<SupportDto.Response> donation(SupportDto.Save dto) {
        return ResponseEntity.ok(supportService.save(dto));

    }

}
