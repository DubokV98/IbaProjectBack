package IbaWork.controller;

import IbaWork.model.Table;
import IbaWork.service.ActionService;
import IbaWork.service.RequestService;
import IbaWork.service.TableService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
public class HomeController {

    @Autowired
    private TableService tableService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ActionService actionService;

    @GetMapping(name = "/home")
    public String selectFieldTable() throws SQLException, JsonProcessingException {
            List<Table> tables = tableService.takeAllTableAndStructure();
            return objectMapper.writeValueAsString(tables);
    }
    @PostMapping(name="/request")
    public String executeRequest(@RequestParam(name = "request") String request, HttpSession httpSession) throws JsonProcessingException {
        String answer="";
        List<Object[]> list;

        request = request.toUpperCase();
        String [] lines = requestService.checkRowRequest(request);

        for(int i = 0; i < lines.length; i++) {
            if (!lines[i].equals("")) {
                httpSession.setAttribute("request", lines[i]);
                    if (requestService.checkSelectAction(lines[i])) {
                        list = requestService.selectExecute(lines[i]);
                        answer = objectMapper.writeValueAsString(list);
                    } else {
                        answer = requestService.executeRequest(lines[i]);
                    }
                httpSession.removeAttribute("request");
            }
        }
        return answer;
    }
}
