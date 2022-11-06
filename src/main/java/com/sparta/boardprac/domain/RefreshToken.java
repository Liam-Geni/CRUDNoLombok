package com.sparta.boardprac.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String memberUsername;

    public RefreshToken() {

    }
    public RefreshToken(String token, String username){
        this.refreshToken = token;
        this.memberUsername = username;
    }

   //Getter
    public String getRefreshToken(){
        return this.refreshToken;
    }

    //update refreshToken
    public RefreshToken updateToken(String token){
        this.refreshToken = token;
        return this;
    }
}
