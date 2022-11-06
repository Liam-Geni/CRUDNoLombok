package com.sparta.boardprac.jwt;


public class TokenDto {

    private String accessToken;
    private String refreshToken;

    public TokenDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public String getAccessToken(){
        return this.accessToken;
    }
    public String getRefreshToken(){
        return this.refreshToken;
    }
}
