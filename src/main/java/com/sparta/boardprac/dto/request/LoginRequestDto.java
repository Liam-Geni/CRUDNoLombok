package com.sparta.boardprac.dto.request;


import javax.validation.constraints.NotBlank;

public class LoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    //기본생성자
    public LoginRequestDto(){}

    //생성자 오버로딩
    public LoginRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }
    //Getter
    public String getUsername(){

        return this.username;
    }
    public String getPassword(){

        return this.password;
    }
}
