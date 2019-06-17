/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import ConstantVariable.Constant;
import Exception.PageLoadTooLongException;

import Utils.Utils;
import java.awt.Robot;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restcontroller.MainController;
import restcontroller.TaskController;

@Service
public class Codenvy {

    @Autowired
    CreateWebdriver createWebdriver;
    @Autowired
    TaskController taskController;
    @Autowired
    Utils utils;
    public WebDriver webDrivers;
    @Autowired
    DowloadService dowloadService;

    public String Start(WebDriver webDriver, String url) throws InterruptedException {
        this.webDrivers = webDriver;
        WebElement element = null;
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        try {
            //webDriver.get(url);

            //js.executeScript("document.body.style.zoom = '0.15'");
            taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
            Thread.sleep(300);

            taskController.getImg("done");
            //webDriver.quit();
        } catch (Exception e) {
            if (e instanceof PageLoadTooLongException) {
                taskController.reportError("Page load too long please submit again");
                webDriver.quit();
                MainController.webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
            } else {
                taskController.reportError("exception" + e.getMessage());
                webDriver.quit();
                MainController.webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
            }

        }

        return "complete";
    }

    public String test(WebDriver webDriver, String url) throws InterruptedException {
        this.webDrivers = webDriver;
        WebElement element = null;
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        try {
            webDriver.get(url);
            System.out.println("run web ok");
            //js.executeScript("document.body.style.zoom = '0.15'");
            taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
            System.out.println("printf img");
            Thread.sleep(300);

            taskController.getImg("done");
            //webDriver.quit();
        } catch (Exception e) {
            if (e instanceof PageLoadTooLongException) {
                taskController.reportError("Page load too long please submit again");
                webDriver.quit();
                MainController.webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
            } else {
                taskController.reportError("exception" + e.getMessage());
                webDriver.quit();
                MainController.webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
            }

        }

        return "complete";
    }

    public void KeepGoogleLive(WebDriver webDriver) throws InterruptedException {
        try {

            Thread.sleep(60000);
            webDriver.get("https://console.cloud.google.com/cloudshell/editor?project=sql1-177218&authuser=0&folder&organizationId&shellonly=true");
            taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
            Thread.sleep(120000);
            utils.sendKeys(new Robot(), "sudo passwd");
            Thread.sleep(6000);

            Actions myAction = null;
            while (true) {
                utils.sendKeys(new Robot(), "sudo passwd");
                Thread.sleep(6000);
                myAction = new Actions(webDriver);
                myAction.sendKeys(Keys.ENTER).build().perform();
                Thread.sleep(5000);
            }

        } catch (Exception ex) {
            taskController.reportError("exception" + ex.getMessage());
            webDriver.quit();
            MainController.webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
        }
    }

    public void loginGoogle(WebDriver webDriver, String user, String pass, String phone) throws IOException, InterruptedException {
        try {
            webDriver.get("https://accounts.google.com/signin");
//            webDriver.get("https://accounts.google.com/signin/v2/identifier?flowName=GlifWebSignIn&flowEntry=ServiceLogin");

            int counter = 0;
            WebElement element = null;
            taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
            System.out.println("c111");
            while (counter < 25) {

                Thread.sleep(400);
                if (utils.waitForPresence(webDriver, 5000, "//input[@type='email' and @id='Email']")) {
                    element = webDriver.findElement(By.xpath("//input[@type='email' and @id='Email']"));
                    element.sendKeys(user);
                    Thread.sleep(600);

                    element = webDriver.findElement(By.xpath("//input[@id='next']"));
                    element.click();
                    Thread.sleep(600);
                    break;
                }
                counter++;

            }

            System.out.println("c222");
            counter = 0;
            while (counter < 25) {
                Thread.sleep(400);
                if (utils.waitForPresence(webDriver, 5000, "//input[@id='Passwd']")) {
                    taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
                    element = webDriver.findElement(By.xpath("//input[@id='Passwd']"));
                    element.sendKeys(pass);
                    Thread.sleep(600);

                    element = webDriver.findElement(By.xpath("//input[@id='signIn']"));
                    element.click();
                    Thread.sleep(600);

                    break;
                }
                counter++;
            }

            System.out.println("c333");
            taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
            taskController.getImg("done");
            counter = 0;
            Thread.sleep(400);
            // hay dung ssh 1 de dang nhap o? may tinh local cho gg nos hieu la ip nay hop le
            // sau do cho ssh 1 nay chay tren vps thi luc do se khong doi` xac thuc nua
//            while (counter < 25) {
//
//                Thread.sleep(400);
//                if (utils.waitForPresence(webDriver, 1000, "//input[@type='submit']")) {
//                    element = webDriver.findElement(By.xpath("//button[@type='submit']"));
//                    element.click();
//                    Thread.sleep(600);
//                    taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
//                    element = webDriver.findElement(By.xpath("//input[@id='idvPreregisteredPhonePin' and @type='tel']"));
//                    while (!MainController.isDone) {
//                        Thread.sleep(600);
//                    }
//
//                    element.sendKeys(MainController.verifyCode);
//                    Thread.sleep(600);
//
//                    element = webDriver.findElement(By.xpath("//input[@type='submit']"));
//                    element.click();
//                    Thread.sleep(600);
//                }
//                counter++;
//            }
        } catch (Exception ex) {
            taskController.reportError("exception" + ex.getMessage());
            webDriver.quit();
            MainController.webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
        }
    }
}
