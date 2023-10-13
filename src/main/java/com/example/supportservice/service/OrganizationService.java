package com.example.supportservice.service;

import com.example.supportservice.dto.OrganizationDto;

import java.util.List;

public interface OrganizationService {

    OrganizationDto.Response save(OrganizationDto.Save dto);

    OrganizationDto.Response update(Long organizationId, OrganizationDto.Update dto);
    List<OrganizationDto.Response> getAllOrganization();


}
