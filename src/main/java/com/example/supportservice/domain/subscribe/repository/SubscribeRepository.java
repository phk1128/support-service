package com.example.supportservice.domain.subscribe.repository;

import com.example.supportservice.domain.organization.model.Organization;
import com.example.supportservice.domain.subscribe.model.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    boolean existsByOrganization(Organization organization);

}
