package com.example.supportservice.service.Impl;

import com.example.supportservice.domain.organization.model.Address;
import com.example.supportservice.domain.organization.model.Organization;
import com.example.supportservice.domain.organization.model.OrganizationStatus;
import com.example.supportservice.domain.organization.repository.OrganizationRepository;
import com.example.supportservice.dto.OrganizationDto;
import com.example.supportservice.exception.AppException;
import com.example.supportservice.exception.ErrorCode;
import com.example.supportservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationDto.Response> getAllOrganization() {


        return organizationRepository.findAll().stream()
                .map(organization -> new OrganizationDto.Response().toDto(organization)) // :: 연산자를 사용할 경우 객체 생성없이 메서드를 사용해야 하므로 toDto가 static 이어야함
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDto.Response save(OrganizationDto.Save dto) {

        if (organizationRepository.existsByNameAndContact(dto.getName(), dto.getContact())) {
            throw new AppException(ErrorCode.ORGANIZATION_DUPLICATED, "이미 등록된 기관 입니다.");
        }

        Organization newOrganization = organizationRepository.save(dto.toEntity());

        return new OrganizationDto.Response().toDto(newOrganization);
    }

    @Override
    public OrganizationDto.Response update(Long organizationId, OrganizationDto.Update dto) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new AppException(ErrorCode.ORGANIZATION_NOT_FOUND, "기관을 찾을 수 없습니다."));


        organization.update(dto.getName(),
                dto.getContact(),
                dto.getUrl(),
                OrganizationStatus.fromString(dto.getStatus()),
                new Address(dto.getCity(), dto.getStreet(), dto.getZipcode()));

        return new OrganizationDto.Response().toDto(organizationRepository.save(organization));
    }
}
