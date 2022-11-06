package com.sparta.boardprac.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupRequestDto {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,16}";


    @NotBlank(message = "Username은 공백일 수 없습니다.")
    @Size(min = 4, max = 12)
    private String username;

    @Pattern(regexp = PASSWORD_REGEX, message = "패스워드는 무조건 영문, 숫자를 1글자 이상 포함해야 합니다.")
    @NotBlank(message = "Password는 공백일 수 없습니다.")
    private String password;

    @NotBlank
    private String passwordConfirm;

    public void setEncodedPwd(String encodedPwd){
        this.password = encodedPwd;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getPasswordConfirm(){
        return this.passwordConfirm;
    }
}
