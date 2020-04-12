package IbaWork.Controller;

import IbaWork.Model.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@CrossOrigin("http://localhost:4200")
@RestController
public class GreetingController {

    private static final String template = "Hello,%s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(name = "/greeting")
    public Greeting greeting(@RequestParam (value="name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),String.format(template,name));
    }
}
