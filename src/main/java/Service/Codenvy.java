/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import ConstantVariable.Constant;
import Exception.PageLoadTooLongException;

import Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
//            webDriver.get(url);

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

    public void KeepGoogleLive(WebDriver webDriver, String user, String pass, String phone) throws InterruptedException {
        try {
            webDriver.get("https://console.cloud.google.com/home/dashboard?project=sql1-177218&authuser=0&folder=&organizationId=");
//            webDriver.get("https://accounts.google.com/signin/v2/identifier?flowName=GlifWebSignIn&flowEntry=ServiceLogin");

            taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
            int counter = 0;

            System.out.println("c111");
            while (counter < 25) {

                Thread.sleep(400);
                if (utils.waitForPresence(webDriver, 5000, "//input[@type='email' and @id='Email']")) {
                    break;
                }
                counter++;

            }

            WebElement element = webDriver.findElement(By.xpath("//input[@type='email' and @id='Email'']"));
            element.sendKeys(user);
            Thread.sleep(600);

            element = webDriver.findElement(By.xpath("//input[@id='next']"));
            element.click();
            Thread.sleep(600);

            System.out.println("c222");
            counter = 0;
            while (counter < 25) {
                Thread.sleep(400);
                if (utils.waitForPresence(webDriver, 5000, "//input[@id='Passwd']")) {
                    break;
                }
                counter++;
            }
            element = webDriver.findElement(By.xpath("//input[@id='Passwd']"));
            element.sendKeys(pass);
            Thread.sleep(600);

            element = webDriver.findElement(By.xpath("//input[@id='signIn']"));
            element.click();
            Thread.sleep(600);

            System.out.println("c333");
            counter = 0;
            while (counter < 25) {
                Thread.sleep(400);
                if (utils.waitForPresence(webDriver, 5000, "//form[@action='/signin/challenge/kpp/5']/button[@type='submit']")) {
                    element = webDriver.findElement(By.xpath("//form[@action='/signin/challenge/kpp/5']/button[@type='submit']"));
                    element.click();
                    Thread.sleep(600);

                    element = webDriver.findElement(By.xpath("//input[@name='phoneNumber' and @type='tel']"));
                    element.sendKeys(phone);
                    Thread.sleep(600);

                    element = webDriver.findElement(By.xpath("//input[@type='submit']"));
                    element.click();
                    Thread.sleep(600);
                    break;
                }
                counter++;
            }

            Thread.sleep(60000);
            webDriver.get("https://console.cloud.google.com/cloudshell/editor?project=sql1-177218&authuser=0&folder&organizationId&shellonly=true");
        } catch (Exception ex) {
            taskController.reportError("exception" + ex.getMessage());
            webDriver.quit();
            MainController.webDriver = createWebdriver.getGoogle(Constant.binaryGoogleHeroku);
        }
    }
}
