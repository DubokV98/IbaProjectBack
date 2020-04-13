package IbaWork.repository;

import IbaWork.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findUserByUsername(String username);

}
