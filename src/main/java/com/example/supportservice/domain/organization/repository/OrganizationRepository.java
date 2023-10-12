package com.example.supportservice.domain.organization.repository;


import com.example.supportservice.domain.organization.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
