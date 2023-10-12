package com.example.supportservice.domain.card.repository;


import com.example.supportservice.domain.card.model.Card;
import com.example.supportservice.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {


    boolean existsByMember(Member member);
    Optional<Card> findByMember(Member member);


}
