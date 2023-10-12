package com.example.supportservice.controller;


import com.example.supportservice.dto.PointDto;
import com.example.supportservice.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/point")
public class PointController {

    private final PointService pointService;

    @PostMapping("/charge")
    public ResponseEntity<PointDto.ResponsePoint> charge(PointDto.Charge dto) {
        return ResponseEntity.ok(pointService.charge(dto));

    }

    @PostMapping("/refunds/{id}")
    public ResponseEntity<PointDto.ResponsePointRefunds> refunds(@PathVariable("id") Long pointId, PointDto.Refunds dto) {
        return ResponseEntity.ok(pointService.refunds(pointId,dto));
    }



}
