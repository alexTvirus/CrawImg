package restcontroller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

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

    public void getScreenShot(String url) throws InterruptedException {
         this.template.convertAndSend("/auto/getScreenShot",url);
    }
    
    public void reportError(String str) throws InterruptedException {
        this.template.convertAndSend("/error/greetings", str);
    }

}
