package com.sparta.boardprac.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.boardprac.dto.request.LoginRequestDto;
import com.sparta.boardprac.dto.request.SignupRequestDto;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
public class Member extends BaseTimeEntity{

    //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;


    //기본 생성자
    public Member(){

    }
//    //전체 필드 생성자 > 생성자 오버로딩
//    public Member(String username, String password){
//        this.username = username;
//        this.password = password;
//    }

    public Member(SignupRequestDto signupRequestDto){
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
    }

    //Getter
    public String getUsername(){

        return this.username;
    }
    public String getPassword(){

        return this.password;
    }
    //Setter
    public void setPassword(String password){
        this.password = password;
    }

    //비밀번호가 맞는지
    public boolean validatePassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password, this.getPassword());
    }

}
