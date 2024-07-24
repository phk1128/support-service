package com.example.supportservice.controller;

import com.example.supportservice.dto.MemberDto;
import com.example.supportservice.service.KakaoLoginService;
import com.example.supportservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/api/v1/member", method = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto.Response> getMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }


    @PostMapping("/join")
    public ResponseEntity<MemberDto.Response> join(@RequestBody MemberDto.Join dto){
        return ResponseEntity.ok(memberService.join(dto));

    }

    @PostMapping("/login")
    public ResponseEntity<MemberDto.Response> login(@RequestBody MemberDto.Login dto, HttpServletResponse response){
        return ResponseEntity.ok(memberService.login(dto.getEmail(), dto.getPassword(), response));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<MemberDto.Response> update(@PathVariable("id") Long memberId, MemberDto.Update dto) {
        return ResponseEntity.ok(memberService.update(memberId,dto));
    }


    @GetMapping("/home")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Home");
    }
}
