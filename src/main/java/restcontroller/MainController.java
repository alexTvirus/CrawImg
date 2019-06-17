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
import Service.DowloadService;
import Utils.ProxyWithSSH;
import Utils.Utils;
import java.awt.Robot;
import java.io.IOException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    CreateWebdriver createWebdriver;
    @Autowired
    Codenvy codenvy;
    @Autowired
    Utils utils;
    @Autowired
    TaskController taskController;
    @Autowired
    DowloadService dowloadService;
    @Autowired
    ProxyWithSSH proxyWithSSH;
    public static WebDriver webDriver = null;
    public static boolean isDone = false;
    public static String verifyCode = "";
    public static Actions myAction1;

    @RequestMapping(value = "/startAuto", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public @ResponseBody
    String startAuto() {
        try {
            if (VariableSession.flag_status_is_first_run_app) {
                webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
                VariableSession.flag_status_is_first_run_app = false;
                myAction1 = new Actions(MainController.webDriver);
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

                        codenvy.test(webDriver, url);
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
                        codenvy.loginGoogle(webDriver, user, pass, phone);
//                        codenvy.KeepGoogleLive(webDriver);
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

    @RequestMapping(value = "/setVerifyCode", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public String setVerifyCode(
            @RequestParam String code) {
        verifyCode = code;
        isDone = true;
        return "index";
    }

    @RequestMapping(value = "/startSpam", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public void startSpam() {
        TaskController.isStart = true;
    }

    @RequestMapping(value = "/stopSpam", method = RequestMethod.GET, headers = "Connection!=Upgrade")
    public void stopSpam() {
        TaskController.isStart = false;
    }

    @RequestMapping(value = "/toado", method = RequestMethod.GET)
    public String testX(
            @RequestParam(value = "x", required = true) int x,
            @RequestParam(value = "y", required = true) int y) {
        try {
            Thread startThread = new Thread() {
                @Override
                public void run() {
                    try {
                        utils.click(x, y);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            };
            startThread.start();

        } catch (Exception e) {
            return "loi : " + e.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/toado1", method = RequestMethod.GET)
    public String toado1(
            @RequestParam(value = "x", required = true) int x,
            @RequestParam(value = "y", required = true) int y) {
        try {
            Thread startThread = new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("clacik1");
                        myAction1 = new Actions(webDriver);
                        myAction1.moveByOffset(x, y).perform();
                        Thread.sleep(2000);
                        myAction1.click().perform();
                        myAction1.moveByOffset(-x, -y).build().perform();
                         System.out.println("clacik2");
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            };
            startThread.start();

        } catch (Exception e) {
            return "loi : " + e.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/cmd", method = RequestMethod.GET)
    public String greeding(
            @RequestParam(value = "x", required = true) String x,
            @RequestParam(value = "y", required = true) String y) {
        String output = "";
        try {
            URL Urlssh = MainController.class
                    .getClassLoader().getResource("runclick.py");
            output = utils.executeCommand("python " + Urlssh.getPath() + " " + x + " " + y);
            return output;
        } catch (Exception e) {
            e.getMessage();
            return e.getMessage();
        }

    }
}
