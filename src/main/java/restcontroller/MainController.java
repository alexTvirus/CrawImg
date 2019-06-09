/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restcontroller;

import ConstantVariable.VariableSession;
import ConstantVariable.Constant;
import Service.Codenvy;
import Service.CreateWebdriver;
import Utils.ProxyWithSSH;
import java.io.IOException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    CreateWebdriver createWebdriver;
    @Autowired
    Codenvy codenvy;

    public static WebDriver webDriver = null;
    public static boolean isDone = false;

    @RequestMapping(value = "/startAuto", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public @ResponseBody
    String startAuto() {
        try {
            if (VariableSession.flag_status_is_first_run_app) {
                webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
                VariableSession.flag_status_is_first_run_app = false;
                ProxyWithSSH proxyWithSSH = new ProxyWithSSH();
                startProxy(proxyWithSSH);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return "running";
    }

    public static void startProxy(ProxyWithSSH proxyWithSSH) {
        try {
            URL Urlssh = MainController.class
                    .getClassLoader().getResource("ssh.txt");
            proxyWithSSH.setting(Urlssh.getPath(), 1080);
            proxyWithSSH.start();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @RequestMapping(value = "/getImg", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public @ResponseBody
    String getImg(@RequestParam String url) {
        try {

            Thread startThread = new Thread() {
                @Override
                public void run() {
                    try {

                        codenvy.Start(webDriver, url);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            };
            startThread.start();
        } catch (Exception e) {
            e.getMessage();
        }
        return "running";
    }

    @RequestMapping(value = "/KeepGoogleLive", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public @ResponseBody
    String KeepGoogleLive(
            @RequestParam String user,
            @RequestParam String pass,
            @RequestParam String phone) {
        try {
            Thread startThread = new Thread() {
                @Override
                public void run() {
                    try {
                        codenvy.KeepGoogleLive(webDriver, user, pass, phone);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            };
            startThread.start();
        } catch (Exception e) {
            e.getMessage();
        }
        return "running";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public String index() {
        return "index";
    }
}
