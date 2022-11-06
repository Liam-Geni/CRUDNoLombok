package com.sparta.boardprac.utils;

import com.sparta.boardprac.Exception.ErrorCode;
import com.sparta.boardprac.Exception.GlobalException;
import com.sparta.boardprac.domain.Member;
import com.sparta.boardprac.domain.RefreshToken;
import com.sparta.boardprac.dto.request.SignupRequestDto;
import com.sparta.boardprac.jwt.JwtUtil;
import com.sparta.boardprac.repository.MemberRepository;
import com.sparta.boardprac.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Optional;

@Slf4j
@Component
public class ValidCheck {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public ValidCheck(MemberRepository memberRepository,
                      RefreshTokenRepository refreshTokenRepository,
                      JwtUtil jwtUtil
    ){
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }
    private Key key;

    //중복 아이디 확인
    public void duplicateMember(SignupRequestDto signupRequestDto){
        if(memberRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new GlobalException(ErrorCode.DUPLICATE_MEMBER_ID);
        }
    }

    //비밀번호 확인
    public void passwordCheck(SignupRequestDto signupRequestDto){
        if(!signupRequestDto.getPassword().equals(signupRequestDto.getPasswordConfirm())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD_CONFIRM);
        }
    }

    //토큰 검증
    public Boolean tokenValidation(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token);
            return true;
        }catch(Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }

    //refreshToken 토큰 검증
    public Boolean refreshTokenValidation(String token){
        //1차 토큰 검증
        if(!tokenValidation(token)) return false;

        //DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByMemberUsername(jwtUtil.getUsernameFromToken(token));
        return refreshTokenOptional.isPresent() && token.equals(refreshTokenOptional.get().getRefreshToken());
    }

    //present member 가입한 사용자 인지
    public Member isPresentMember(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

}
