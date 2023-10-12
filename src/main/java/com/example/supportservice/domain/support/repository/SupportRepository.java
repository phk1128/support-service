package com.example.supportservice.domain.support.repository;

import com.example.supportservice.domain.support.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support,Long> {

}
