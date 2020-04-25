package IbaWork.service;

import IbaWork.model.Action;
import IbaWork.model.User;
import IbaWork.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionService{

    @Autowired
    private ActionRepository actionRepository;

    public void saveAction(String request, String result){
        LocalDate date = LocalDate.now();

        User user = getCurrentUser();
        LocalDateTime timeNow = LocalDateTime.now().withNano(0);
        LocalTime localTime = timeNow.toLocalTime();

        Action action = new Action();

        action.setRequest(request);
        action.setDate(date);
        action.setTime(localTime.toString());
        action.setResult(result);
        action.setUser(user);

        actionRepository.save(action);
    }

    public List<Action> findAllActionTodayCurrentUser() throws ParseException {
        User user = getCurrentUser();
        List<Action> actionListAll = actionRepository.findAllByUser(user);
        return sortByToday(actionListAll);
    }

    public List<Action> findAllActionUser(){
        User user = getCurrentUser();
        List<Action> actionList = actionRepository.findAllByUser(user);
        return actionList;
    }

    public User getCurrentUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public List<Action> sortByToday(List<Action> actionList) throws ParseException {
        LocalDate localDate = LocalDate.now();
        List<Action> resultActionList = new ArrayList<>();
        for (Action action: actionList) {
            String actionDate = action.getDate().toString();
            actionDate = actionDate.substring(0,10);
            if(actionDate.equals(localDate.toString())) {
                resultActionList.add(action);
            }
        }
        return resultActionList;
    }
}
