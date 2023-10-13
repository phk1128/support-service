package com.example.supportservice.domain.support.repository;

import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.support.model.Support;
import com.example.supportservice.dto.SupportDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SupportRepository extends JpaRepository<Support,Long> {

    List<Support> findAllByMember(Member member);

}
