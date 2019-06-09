package restcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GreedingController {

    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public String index() {
        return "index";
    }
}
