package makeBlog.cjblog.springbootdeveloper.service;


import lombok.RequiredArgsConstructor;
import makeBlog.cjblog.springbootdeveloper.config.jwt.TokenProvider;
import makeBlog.cjblog.springbootdeveloper.domain.User;
import makeBlog.cjblog.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken (String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
