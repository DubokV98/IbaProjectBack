package IbaWork.service;

import IbaWork.model.User;
import IbaWork.config.ValidatorBean;
import IbaWork.repository.RequestRepositoryImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    @Autowired
    RequestRepositoryImpl requestRepository;

    @Autowired
    UserService userService;

    public String executeRequest(String request) throws ConstraintViolationException {
        User user = userService.userBySecurityContext();
        if(user.isAdmin()) {
            if(checkActionAdmin(request)){
                if(checkDB(request)) {
                    return requestRepository.executeRequest(request);
                }
            }
        }
        return "You do not have rights!";
    }

    public boolean checkProhibitedTables(String request) {
        for (int j = 0; j < ValidatorBean.prohibitedTables.size(); j++) {
            if (request.contains(ValidatorBean.prohibitedTables.get(j))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkActionAdmin(String request){
        //Check validActionAdmin
        for (int i = 0; i < ValidatorBean.validActionAdmin.size(); i++) {
            if (request.contains(ValidatorBean.validActionAdmin.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkActionUser(String request){
        for (int j = 0; j < ValidatorBean.prohibitedTables.size(); j++) {
            if (request.contains(ValidatorBean.prohibitedTables.get(j))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSelectAction(String request) {
        if(request.contains(ValidatorBean.SELECT)){
            return true;
        }
        return false;
    }

    public String[] checkRowRequest(String request){
        request = request.replaceAll(";",";\n");
        String[] lines = request.split("\n");
        return lines;
    }

    public boolean checkDB(String request){
        String[]lines = request.split("\\.");
        for (String line: lines) {
            for(int i = 0; i < ValidatorBean.validDB.size(); i++) {
                if (line.contains(ValidatorBean.validDB.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<Object[]> selectExecute(String request) throws ConstraintViolationException {
        List<Object[]> list = null;
        if(!checkProhibitedTables(request)) {
            if(checkDB(request)){
                list = requestRepository.selectRequest(request);
            }
        }
        return list;
    }
}
