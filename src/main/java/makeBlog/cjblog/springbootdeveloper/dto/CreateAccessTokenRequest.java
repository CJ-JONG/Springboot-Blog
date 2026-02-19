package makeBlog.cjblog.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IProcessingInstruction;

@Getter
@Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}
