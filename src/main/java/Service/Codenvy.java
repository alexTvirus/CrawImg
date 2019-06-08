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
        boolean flag_wait = false;
        WebElement element = null;
        boolean flag_have = true;
        int counter = 0;
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        try {
            webDriver.get(url);
            counter = 0;
            while (counter < 10) {
                if (utils.waitForPresent(webDriver, 100, "//img")) {
                        break;
                }
                //check connect
                Thread.sleep(200);
                if (counter == 9) {
                    throw new PageLoadTooLongException();
                }
                counter++;
            }

            List<WebElement> listelements = null;
            listelements = new ArrayList<>();
            js.executeScript("document.body.style.zoom = '0.15'");
            taskController.getScreenShot(dowloadService.dowloadImgTypeBase64(webDriver));
            Thread.sleep(300);
            listelements = webDriver.findElements(By.xpath("//img"));
            if (listelements != null && listelements.size() > 1) {
                for (WebElement item : listelements) {
                    if (utils.isAttribtuePresent(item, "src")) {
                        taskController.getImg(item.getAttribute("src"));
                    }
                }
            }

//            listelements = webDriver.findElements(By.xpath("//div[@class='box-image']/div/a/img"));
//            if (listelements != null && listelements.size() > 1) {
//                for (WebElement item : listelements) {
//                    taskController.getImg(item.getAttribute("data-lazy-src"));
//
//                }
//            }
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
}
