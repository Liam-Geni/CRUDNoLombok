package com.sparta.boardprac.dto.response;


import com.sparta.boardprac.domain.Member;

public class MemberResponseDto {
    private String username;
    private String password;

    //기본생성자
    public MemberResponseDto(){}

    //생성자 오버로딩
    public MemberResponseDto(String username, String password){
        this.username = username;
        this.password = password;
    }

    public MemberResponseDto(Member member){
        this.username = member.getUsername();
        this.password = member.getPassword();
    }
    //Getter
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

}
