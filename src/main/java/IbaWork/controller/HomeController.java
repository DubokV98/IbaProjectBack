package IbaWork.controller;

import IbaWork.model.Action;
import IbaWork.model.Select;
import IbaWork.model.Table;
import IbaWork.model.Value;
import IbaWork.service.ActionService;
import IbaWork.service.RequestService;
import IbaWork.service.TableService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:4200")
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
            List<Table> tables = tableService.selectTablesAndStructure();
            return objectMapper.writeValueAsString(tables);
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public String executeRequest(@RequestParam(required = false, name = "request") String request,
                                 HttpSession httpSession) throws JsonProcessingException {
        String answer="ERROR";
        List<Object[]> list;

        request = request.toUpperCase();
        String [] lines = requestService.checkRowRequest(request);

        for(int i = 0; i < lines.length; i++) {
            if (!lines[i].equals("")) {
                httpSession.setAttribute("request", lines[i]);
                    if (requestService.checkSelectAction(lines[i])) {
                        Select select = requestService.selectExecute(lines[i]);
                        answer = objectMapper.writeValueAsString(select);
                    } else {
                        answer = requestService.executeRequest(lines[i]);
                        answer = objectMapper.writeValueAsString(answer);
                    }
                httpSession.removeAttribute("request");
            }
        }
        return answer;
    }

    @RequestMapping(value = "/actionToday", method = RequestMethod.GET)
    public String takeAllTodayRequest() throws JsonProcessingException, ParseException {
        List<Action> actionList = actionService.findAllActionTodayCurrentUser();
        return objectMapper.writeValueAsString(actionList);
    }

    @RequestMapping(value = "/actionAll", method = RequestMethod.GET)
    public String takeAllRequest() throws JsonProcessingException, ParseException {
        List<Action> actionList = actionService.findAllActionUser();
        return objectMapper.writeValueAsString(actionList);
    }

}
