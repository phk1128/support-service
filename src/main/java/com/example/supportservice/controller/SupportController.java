package com.example.supportservice.controller;


import com.example.supportservice.dto.SupportDto;
import com.example.supportservice.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/donation")
    public ResponseEntity<SupportDto.Response> donation(@RequestBody SupportDto.Save dto) {
        return ResponseEntity.ok(supportService.save(dto));

    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<SupportDto.Response>> getAllSupport(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(supportService.getAllSupport(memberId));

    }

}
