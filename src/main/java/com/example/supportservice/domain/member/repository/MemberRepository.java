package com.example.supportservice.domain.member.repository;

import com.example.supportservice.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

}
