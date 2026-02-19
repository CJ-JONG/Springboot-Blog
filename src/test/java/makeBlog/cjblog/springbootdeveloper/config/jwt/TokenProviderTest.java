package makeBlog.cjblog.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import lombok.val;
import makeBlog.cjblog.springbootdeveloper.domain.User;
import makeBlog.cjblog.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {

        User testUser = userRepository.save(User.builder()
                .email("user@email.com")
                .password("test")
                .build());

        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }


    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build().createtoken(jwtProperties);

       boolean result = tokenProvider.validToken(token);

       assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void  validToken_validToken() {
        String token = JwtFactory.withDefaultValues()
                .createtoken(jwtProperties);

        boolean result = tokenProvider.validToken(token);

        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        String userEmail = "user@emal.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createtoken(jwtProperties);

        Authentication authentication = tokenProvider.getAuthentication(token);

       assertThat(((UserDetails) authentication.getPrincipal()).getUsername())
                .isEqualTo(userEmail);
    }

    @DisplayName("getUserId(): 토큰으로 유저ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createtoken(jwtProperties);

        Long userIdByToken = tokenProvider.getUserId(token);

        assertThat(userIdByToken).isEqualTo(userId);
    }


}