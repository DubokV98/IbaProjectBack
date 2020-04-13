package IbaWork.Controller;


import IbaWork.Model.Answer;
import IbaWork.Model.AuthenticationBean;
import IbaWork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

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

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public Answer addNewUser(@RequestParam(required = false ,name = "username") String username,
                             @RequestParam(required = false, name = "password_first") String password_first,
                             @RequestParam(required = false, name = "password_second") String password_second) {
        Answer answer = new Answer(userService.addNewUser(username, password_first, password_second));
        return answer;
    }
}
