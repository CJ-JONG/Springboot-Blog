package makeBlog.cjblog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import makeBlog.cjblog.springbootdeveloper.dto.CreateAccessTokenRequest;
import makeBlog.cjblog.springbootdeveloper.dto.CreateAccessTokenResponse;
import makeBlog.cjblog.springbootdeveloper.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
            (@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).
                body(new CreateAccessTokenResponse(newAccessToken));
    }
}
