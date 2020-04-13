package IbaWork.service;

import IbaWork.Model.Role;
import IbaWork.Model.User;
import IbaWork.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collections;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    userRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

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
            return message;
        } else {
            user = new User();
            if(password_first != "" && password_first.equals(password_second)) {
                String encryptPassword = passwordEncoder.encode(password_first);
                user.setPassword(encryptPassword);
            }else{
                message = "Password are different!";
                return message;
            }
            user.setUsername(username);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
        }
        return message;
    }
}
