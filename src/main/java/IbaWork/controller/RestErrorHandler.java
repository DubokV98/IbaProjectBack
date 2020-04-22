package IbaWork.controller;

import IbaWork.service.ActionService;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {
    @Autowired
    ActionService actionService;

    @ExceptionHandler (ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintDuplicate(ConstraintViolationException ex, HttpSession httpSession){
        saveBadRequest(ex,httpSession);
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (SQLGrammarException.class)
    public ResponseEntity<String> handleConstraintErrorSyntax(SQLGrammarException ex, HttpSession httpSession){
        saveBadRequest(ex,httpSession);
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public void saveBadRequest(JDBCException ex, HttpSession httpSession)
    {
        String request = (String) httpSession.getAttribute("request");
        httpSession.removeAttribute("request");
        int code = ex.getErrorCode();
        String message = ex.getSQLException().getMessage();
        String result = "Error code: " + code + ". " + message;
        actionService.saveAction(request,result);
    }
}

