/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import ConstantVariable.Constant;
import Entity.ObjectJson;
import Exception.CantGetMainPageException;
import Exception.DisconnectException;
import Exception.PageLoadTooLongException;
import Exception.VerifiMobileException;
import Utils.Chuyen_tu_Object_sang_byte_de_doc_hoac_ghi_file;
import Utils.Utils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restcontroller.TaskController;

@Service
public class Codenvy {

    @Autowired
    TaskController taskController;
    @Autowired
    Utils utils;
    public WebDriver webDrivers;

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
            while (counter < 25) {
                if (utils.waitForPresent(webDriver, 100, "//img[@width='150']")) {
                    if (utils.waitForPresence(webDriver, 100, "//img[@width='150']")) {
                        break;
                    }
                }
                //check connect
                Thread.sleep(400);
                if (counter == 24) {
                    throw new PageLoadTooLongException();
                }
                counter++;
            }

            List<WebElement> listelements = null;

            Actions action = new Actions(webDriver);
            listelements = new ArrayList<>();
            listelements = webDriver.findElements(By.xpath("//img[@class='attachment-woocommerce_thumbnail size-woocommerce_thumbnail lazyloaded']"));

            if (listelements != null && listelements.size() > 1) {
                action.moveToElement(listelements.get(3)).perform();
                counter = 0;
                while (counter < 5) {
                    if (utils.waitForPresent(webDriver, 100, "//button[@class='flickity-button flickity-prev-next-button next']")) {
                        break;
                    }
                    //check connect
                    Thread.sleep(400);
                    if (counter == 4) {
                        flag_have = false;
                    }
                    counter++;
                }
                if (flag_have) {
                    webDriver.findElement(By.xpath("//button[@class='flickity-button flickity-prev-next-button next']")).click();
                    action.moveToElement(listelements.get(7)).perform();
                    webDriver.findElements(By.xpath("//button[@class='flickity-button flickity-prev-next-button next']")).get(2).click();
                }
            }

            js.executeScript("document.body.style.zoom = '0.3'");
            listelements = webDriver.findElements(By.xpath("//div[@class='box-image']/div/a/img"));
            if (listelements != null && listelements.size() > 1) {
                for (WebElement item : listelements) {
                    if (utils.isAttribtuePresent(item, "scrset")) {
                        taskController.getImg(item.getAttribute("src"));
                    } else {
                        String array[] = item.getAttribute("data-lazy-srcset").split("250w", 2);
                        taskController.getImg(array[0]);
                    }
                }
            }
            taskController.getImg("done");
            webDriver.quit();
        } catch (Exception e) {
            if (e instanceof PageLoadTooLongException) {
                taskController.reportError("Page load too long please submit again");
                webDriver.quit();
            } else if (e instanceof CantGetMainPageException) {
                taskController.reportError("Page load too long please reset");
                webDriver.quit();
            } else {
                webDriver.quit();
            }

        }

        return "complete";
    }
}
