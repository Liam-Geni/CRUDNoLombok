package com.sparta.boardprac.repository;

import com.sparta.boardprac.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberUsername(String usernameFromToken);
}
