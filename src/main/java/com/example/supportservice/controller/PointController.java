package com.example.supportservice.controller;


import com.example.supportservice.dto.PointDto;
import com.example.supportservice.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/point")
public class PointController {

    private final PointService pointService;

    @PostMapping("/charge/{memberId}")
    public ResponseEntity<PointDto.ResponsePoint> charge(@PathVariable("memberId") Long memberId,@RequestBody PointDto.Charge dto) {
        return ResponseEntity.ok(pointService.charge(memberId,dto));

    }

    @PostMapping("/refunds/{memberId}")
    public ResponseEntity<PointDto.ResponsePointRefunds> refunds(@PathVariable("memberId") Long memberId, @RequestBody PointDto.Refunds dto) {
        return ResponseEntity.ok(pointService.refunds(memberId,dto));
    }



}
