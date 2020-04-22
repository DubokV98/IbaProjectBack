package IbaWork.service;

import IbaWork.model.Action;
import IbaWork.model.User;
import IbaWork.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActionService{

    @Autowired
    private ActionRepository actionRepository;

    public void saveAction(String request, String result){
        Date date = new Date();
        User user = getCurrentUser();

        Action action = new Action();

        action.setRequest(request);
        action.setDate(date);
        action.setResult(result);
        action.setUser(user);

        actionRepository.save(action);
    }

    public List<Action> findAllActionCurrentUser(){
        User user = getCurrentUser();
        return actionRepository.findAllByUser(user);
    }

    public User getCurrentUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
