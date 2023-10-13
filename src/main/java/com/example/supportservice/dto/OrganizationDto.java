package com.example.supportservice.dto;

import com.example.supportservice.domain.organization.model.Address;
import com.example.supportservice.domain.organization.model.Organization;
import com.example.supportservice.domain.organization.model.OrganizationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrganizationDto {

    @Getter
    public static class Save {

        private String name;
        private String contact;
        private String url;
        private String status;
        private String city;
        private String street;
        private String zipcode;

        public Organization toEntity() {
            return Organization.builder()
                    .name(name)
                    .contact(contact)
                    .url(url)
                    .status(OrganizationStatus.fromString(status))
                    .address(new Address(city,street,zipcode))
                    .build();
        }



    }

    @Getter
    public static class Update {

        private String name;
        private String contact;
        private String url;
        private String status;
        private String city;
        private String street;
        private String zipcode;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long organizationId;
        private String name;
        private String contact;
        private String url;
        private Address address;
        private OrganizationStatus status;

        public Response toDto(Organization organization){
            return Response.builder()
                    .organizationId(organization.getId())
                    .name(organization.getName())
                    .contact(organization.getContact())
                    .url(organization.getUrl())
                    .address(organization.getAddress())
                    .status(organization.getStatus())
                    .build();
        }


    }
}
