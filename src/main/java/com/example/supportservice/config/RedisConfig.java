package com.example.supportservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;


@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.host}")
    private String host;

    /*Redis 연결을 위한 RedisConnection 객체 생성
    LettuceConnectionFactory 클래스는 비동기 처리를 지원하고 성능이 좋음
    */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }


//    /*Redis 데이터를 저장하고 조회하기 위한 RedisTemplate 객체 생성
//    * RedisRepository를 이용할것이므로 해당로직은 사용 X
//    * */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//
//        return redisTemplate;
//
//    }
}
