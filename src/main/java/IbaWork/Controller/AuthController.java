package IbaWork.Controller;

import IbaWork.Model.AuthenticationBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin("http://localhost:4200")
public class AuthController {
    @GetMapping(path="/auth")
    public AuthenticationBean helloWorld() { return new AuthenticationBean("You are authenticated"); }

    @GetMapping(path="/ss")
    public AuthenticationBean ss()
    {
        return new AuthenticationBean("ss");
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
