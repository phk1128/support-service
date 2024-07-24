//package com.example.supportservice.controller;
//
//
//
//
//import com.example.supportservice.domain.member.model.MemberStatus;
//import com.example.supportservice.dto.MemberDto;
//import com.example.supportservice.service.KakaoLoginService;
//import com.example.supportservice.service.MemberService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//
//import java.time.LocalDateTime;
//
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@WebMvcTest(MemberController.class)
//public class MemberControllerTest {
//
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//  /*  MemberController에서 주입받은 Bean 객체에 대해 Mock 형태의 객체를 생성해줌,
//    MemberController에 주입 받은 객체는 모두 Mock으로 만들어야 오류가 안남 */
//
//    @MockBean
//    MemberService memberService;
//
//    @MockBean
//    KakaoLoginService kakaoLoginService;
//
//    // @WithMockUser를 사용하여 스프링 시큐리티 인증을 패스한다.
//    @Test
//    @DisplayName("member 데이터 가져오기 테스트")
//    @WithMockUser(username = "test", password = "test")
//    void member조회컨트롤러테스트() throws Exception {
//
//        //given
//        MemberDto.Response dto = MemberDto.Response.builder()
//                .memberId(1L)
//                .status(MemberStatus.ACTIVE)
//                .name("박형균")
//                .email("test@test.com")
//                .message("테스트조회완료")
//                .joinDate(LocalDateTime.now().toString())
//                .build();
//
//        long memberId = 1L;
//
//        given(memberService.getMember(memberId)).willReturn(dto);
//
//
//        mockMvc.perform(get("/api/v1/member/" + memberId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.memberId").exists())
//                .andExpect(jsonPath("$.status").exists())
//                .andExpect(jsonPath("$.name").exists())
//                .andExpect(jsonPath("$.email").exists())
//                .andExpect(jsonPath("$.message").exists())
//                .andExpect(jsonPath("$.joinDate").exists())
//                .andDo(print());
//
//        // 해당 객체의 메소드가 실행되었는지 체크
//        verify(memberService).getMember(1L);
//
//
//    }
//
//
//}
