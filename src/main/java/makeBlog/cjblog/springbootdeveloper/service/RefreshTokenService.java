package makeBlog.cjblog.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import makeBlog.cjblog.springbootdeveloper.domain.RefreshToken;
import makeBlog.cjblog.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken (String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected token"));
    }
}
