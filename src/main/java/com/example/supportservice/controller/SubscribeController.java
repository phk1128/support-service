package com.example.supportservice.controller;


import com.example.supportservice.dto.SubscribeDto;
import com.example.supportservice.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/subscribe")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/regular-donation/{memberId}")
    public ResponseEntity<SubscribeDto.Response> save(@PathVariable("memberId") Long memberId, @RequestBody SubscribeDto.Save dto){
        return ResponseEntity.ok(subscribeService.save(memberId, dto));

    }

    @PostMapping("/update-all")
    public ResponseEntity<List<String>> updateAll() {

        return ResponseEntity.ok(subscribeService.updateAllSubscribe());

    }


}
