package com.example.supportservice.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;


@AllArgsConstructor
@Getter
@RedisHash(value = "JWT", timeToLive = 14*24*60*60) // 14일동안 데이터 보존
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;


    /*accessToken 필드값으로 Redis에서 HASH 데이터 타입에 대한 필드를 인덱싱하기 위해 사용
    @Indexed가 없으면 accessToken으로 RefreshToken객체를 찾을 수 없다.*/
    @Indexed
    private String accessToken;

    public void updateAccessToken(String newAccessToken){
        this.accessToken = newAccessToken;

    }
}
