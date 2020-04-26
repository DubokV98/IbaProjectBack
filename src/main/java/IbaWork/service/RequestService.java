package IbaWork.service;

import IbaWork.model.Select;
import IbaWork.model.Structure;
import IbaWork.model.User;
import IbaWork.config.ValidatorBean;
import IbaWork.model.Value;
import IbaWork.repository.RequestRepositoryImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {
    @Autowired
    RequestRepositoryImpl requestRepository;

    @Autowired
    TableService tableService;

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

    public boolean checkSelectAction(String request) {
        for (String validAction : ValidatorBean.validActionAdmin) {
            if(request.contains(validAction)){
                return false;
            }
        }

        if(request.contains(ValidatorBean.SELECT)){
            return true;
        }
        return false;
    }

    public String[] checkRowRequest(String request){
        request = request.replaceAll("\n"," ");
        String[] lines = request.split(";");
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

    public Select takeSelectTableStructure(String request){

        String dbAndTable = tableService.takeDbTableFromRequest(request);

        List<Object[]> structureObjList = tableService.takeCurrentTable(dbAndTable);

        Structure[] structures = new Structure[structureObjList.size()];
        Select select = new Select();
        int i = 0;

        for (Object[] obj: structureObjList) {
            structures[i] = (tableService.takeStructure(obj));
            i++;
        }
        select.setStructures(structures);
        return select;
    }

    public Select selectExecute(String request) throws ConstraintViolationException {
        Select select = new Select();
        if(!checkProhibitedTables(request)) {
            if(checkDB(request)){
                select = takeSelectTableStructure(request);
                List<Object[]> list = requestRepository.selectRequest(request);
                select = parseToListValue(list, select);
            }
        }
        return select;
    }

    public Select parseToListValue(List<Object []> objectList, Select select) {

        List<Value> valueList = new ArrayList<>();
        int numberFieldsTable = select.getStructures().length;

        for (Object[] object: objectList) {

            Value value = new Value();
            String[] stringMas = new String[numberFieldsTable];

            for (int i = 0; i < numberFieldsTable; i++) {
                stringMas[i]= String.valueOf(object[i]);
            }

            value.setValue(stringMas);
            valueList.add(value);
        }
        select.setValuesList(valueList);
        return select;
    }
}

