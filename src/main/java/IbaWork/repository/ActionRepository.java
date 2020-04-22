package IbaWork.repository;

import IbaWork.model.Action;
import IbaWork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {
    Action save(Action action);
    List<Action> findAllByUser(User user);
}
