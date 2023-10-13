package com.example.supportservice.controller;


import com.example.supportservice.dto.OrganizationDto;
import com.example.supportservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping("/register")
    public ResponseEntity<OrganizationDto.Response> save(@RequestBody OrganizationDto.Save dto) {
        return ResponseEntity.ok(organizationService.save(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<OrganizationDto.Response>> getAllOrganization() {
        return ResponseEntity.ok(organizationService.getAllOrganization());

    }

    @PatchMapping("/update/{organizationId}")
    public ResponseEntity<OrganizationDto.Response> update(@PathVariable("organizationId") Long organizationId, @RequestBody OrganizationDto.Update dto){
        return ResponseEntity.ok(organizationService.update(organizationId,dto));
    }
}
