package makeBlog.cjblog.springbootdeveloper.httpapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpApiController {

    @GetMapping("/http/api")
    public String httpApi() {
        return "hello";
    }
}
