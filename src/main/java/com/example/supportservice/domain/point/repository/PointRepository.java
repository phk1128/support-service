package com.example.supportservice.domain.point.repository;


import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.point.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByMember(Member member);
}
