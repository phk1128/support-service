package com.example.supportservice.domain.member.repository;

import com.example.supportservice.domain.member.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    /*accessToken에 @Indexed를 붙였으므로 이를 이용해서 RefreshToken의 객체를 찾을 수 있다.*/
    Optional<RefreshToken> findByAccessToken(String accessToken);
}
