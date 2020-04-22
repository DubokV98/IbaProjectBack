package IbaWork.service;

import IbaWork.model.Role;
import IbaWork.model.User;
import IbaWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String addNewUser(String username, String password_first, String password_second ) {
        String message = "User created!";
        User user;
        user = userRepository.findByUsername(username);
        if (user != null) {
            message = "User exist!";
        } else {
            user = new User();
            if(password_first != "" && password_first.equals(password_second)) {
                String encryptPassword = passwordEncoder.encode(password_first);
                user.setPassword(encryptPassword);
            }else{
                message = "Password are different!";
            }
            user.setUsername(username);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
        }
        return message;
    }

    public User userBySecurityContext(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

}
