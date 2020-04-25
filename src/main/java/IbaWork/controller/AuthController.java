package IbaWork.controller;


import IbaWork.model.Answer;
import IbaWork.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/user")
    public String user(Principal user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public Answer addNewUser(@RequestParam(required = false, name = "username") String username,
                             @RequestParam(required = false, name = "password_first") String password_first,
                             @RequestParam(required = false, name = "password_second") String password_second) {
        Answer answer = new Answer(userService.addNewUser(username, password_first, password_second));
        return answer;
    }

    @RequestMapping("/token")
    public Map<String,String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }
}
