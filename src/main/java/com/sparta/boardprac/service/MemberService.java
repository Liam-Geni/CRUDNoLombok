package com.sparta.boardprac.service;

import com.sparta.boardprac.Exception.ErrorCode;
import com.sparta.boardprac.Exception.GlobalException;
import com.sparta.boardprac.domain.Member;
import com.sparta.boardprac.domain.RefreshToken;
import com.sparta.boardprac.dto.ResponseDto;
import com.sparta.boardprac.dto.request.LoginRequestDto;
import com.sparta.boardprac.dto.request.SignupRequestDto;
import com.sparta.boardprac.dto.response.MemberResponseDto;
import com.sparta.boardprac.dto.response.LoginResponseDto;
import com.sparta.boardprac.jwt.JwtUtil;
import com.sparta.boardprac.jwt.TokenDto;
import com.sparta.boardprac.repository.MemberRepository;
import com.sparta.boardprac.repository.RefreshTokenRepository;
import com.sparta.boardprac.utils.ValidCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ValidCheck check;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    //생성자 의존성 주입
    public MemberService(MemberRepository memberRepository,
                         ValidCheck check,
                         PasswordEncoder passwordEncoder,
                         JwtUtil jwtUtil,
                         RefreshTokenRepository refreshTokenRepository
    ){
        this.memberRepository = memberRepository;
        this.check = check;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    //회원가입
    @Transactional
    public ResponseEntity<ResponseDto<MemberResponseDto>> registerMember(SignupRequestDto signupRequestDto){
        //중복아이디 확인
        check.duplicateMember(signupRequestDto);

        //비밀번호 체크
        check.passwordCheck(signupRequestDto);

        //새로운 사용자 정보 힙영역에 임시저장
        Member member = new Member(signupRequestDto);
        //비밀번호 인코딩
        member.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        //새로운 사용자 정보 DB저장
        memberRepository.save(member);
        return ResponseEntity.ok().body(ResponseDto.success(new MemberResponseDto(member)));
    }

    //로그인
    @Transactional
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Member member = check.isPresentMember(loginRequestDto.getUsername());

        //사용자가 있는지
        if(null == member){
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //비밀번호가 맞는지
        if(!member.validatePassword(passwordEncoder, loginRequestDto.getPassword())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD);
        }

        //위에서 예외를 통과 하였을때 토큰 생성
        TokenDto tokenDto = jwtUtil.createAllToken(loginRequestDto.getUsername());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberUsername(loginRequestDto.getUsername());

        if(refreshToken.isPresent()){
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else{
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getUsername());
            refreshTokenRepository.save(newToken);
        }
        setHeader(response, tokenDto);

        return ResponseEntity.ok(ResponseDto.success(new LoginResponseDto(member)));
    }
    private void setHeader(HttpServletResponse response, TokenDto tokenDto){
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
