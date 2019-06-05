package restcontroller;

import ConstantVariable.Constant;
import Service.Codenvy;
import Service.CreateWebdriver;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import Service.DowloadService;
import Service.ProxyWithSSH;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableScheduling
@Controller
public class TaskController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String string) throws Exception {
        Thread.sleep(3000); // simulated delay
        return string;
    }

    public void greeting() throws InterruptedException {
    }

    public void looperAuto() throws InterruptedException {
    }
    
    public void getImg(String url) throws InterruptedException {
         this.template.convertAndSend("/auto/getImg",url);
    }
    
    public void reportError(String str) throws InterruptedException {
        this.template.convertAndSend("/error/greetings", str);
    }

}
