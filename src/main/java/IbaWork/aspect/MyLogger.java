package IbaWork.aspect;

import IbaWork.service.ActionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class MyLogger {
    @Autowired
    private ActionService actionService;

    public static final int FIRST_ARGUMENT = 0;


   // @Pointcut("execution (public * IbaWork.service.RequestService.executeRequest())")
    @AfterReturning(value = "execution (public * IbaWork.service.RequestService.executeRequest(..))", returning = "result")
    public void afterCallMethodExecuteRequest(JoinPoint joinPoint, String result){
        String request = (String) joinPoint.getArgs()[FIRST_ARGUMENT];
        actionService.saveAction(request, result);
    }

    @AfterReturning(value = "execution (public * IbaWork.service.RequestService.selectExecute(..))", returning = "list")
    public void afterCallMethodSelectExecute(JoinPoint joinPoint, List<Object[]> list){
        String request = (String) joinPoint.getArgs()[FIRST_ARGUMENT];
        String result = list.size()+ " row(s) returned";
       actionService.saveAction(request, result);
    }

}
