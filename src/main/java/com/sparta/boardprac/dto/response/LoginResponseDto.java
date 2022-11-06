package com.sparta.boardprac.dto.response;


import com.sparta.boardprac.domain.Member;

public class LoginResponseDto {
    private String username;

    //기본 생성자
    public LoginResponseDto(){}

    //생성자
    public LoginResponseDto(Member member){
        this.username = member.getUsername();
    }
    //Getter
    public String getUsername(){
        return this.username;
    }
}
