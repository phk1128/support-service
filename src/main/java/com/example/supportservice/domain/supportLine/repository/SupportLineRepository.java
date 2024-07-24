package com.example.supportservice.domain.supportLine.repository;

import com.example.supportservice.domain.supportLine.model.SupportLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportLineRepository extends JpaRepository<SupportLine,Long> {
}
