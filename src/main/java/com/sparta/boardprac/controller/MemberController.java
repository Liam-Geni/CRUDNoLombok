package com.sparta.boardprac.controller;

import com.sparta.boardprac.dto.ResponseDto;
import com.sparta.boardprac.dto.request.LoginRequestDto;
import com.sparta.boardprac.dto.request.SignupRequestDto;
import com.sparta.boardprac.dto.response.MemberResponseDto;
import com.sparta.boardprac.dto.response.LoginResponseDto;
import com.sparta.boardprac.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    //생성자 의존성 주입
    public MemberController(MemberService memberService){

        this.memberService = memberService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<MemberResponseDto>> registerMember(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return memberService.registerMember(signupRequestDto);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> loginMember(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return memberService.login(loginRequestDto, response);
    }
}
