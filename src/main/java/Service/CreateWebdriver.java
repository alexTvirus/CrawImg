package Service;

import Bean.SystemConfig;
import java.io.File;
import java.net.InetAddress;
import java.util.Collections;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateWebdriver {

    @Autowired
    PathDriver setPathDriver;
    @Autowired
    SystemConfig SystemCofig;

    public WebDriver getFirefox(String binaryFirefox) {
        setPathDriver.setPathFireFox();
        //
        WebDriver webDriver = null;
        try {
            System.setProperty(PathDriver.webDriverFirefox, PathDriver.dirDriverFirefox);
//            File pathToBinary = new File(binaryFirefox);
//            FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
//            FirefoxProfile firefoxProfile = new FirefoxProfile();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("network.proxy.type", 1);
            options.addPreference("network.proxy.socks", "127.0.0.1");
            options.addPreference("network.proxy.socks_port", 1080);
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("network.proxy.socks_version", 4);
//            options.addArguments("--headless");
            webDriver = new FirefoxDriver(options);
            return webDriver;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return webDriver;
        }
    }

    // neu file binary de o? thuc muc khac' khong phai /user/bin , vi du heroku
    public WebDriver getGoogle(String binaryGoogle) {
        //
        setPathDriver.setPathGoogle();
        //
        WebDriver webDriver = null;
        try {
            //
            System.setProperty(PathDriver.webDriverGoogle, PathDriver.dirDriverGoogle);

            switch (SystemCofig.os) {
                case "Linux":
                    ChromeOptions options = new ChromeOptions();
                    options.setBinary(binaryGoogle);
//                    String str_proxy_linux = "--proxy-server=socks4://"+InetAddress.getLocalHost().getHostAddress()+":1080";
                    // String str_proxy_linux = "--proxy-server=socks4://127.0.0.1:1080";
                    //  options.addArguments(str_proxy_linux);
//                    options.addArguments("user-data-dir=/app/profile/");
                    options.addArguments("disable-infobars");
                    options.addArguments("--start-maximized");
//                    options.addArguments("chrome.switches", "--disable-extensions");
			//options.addArguments("--headless");
//                    options.addArguments("--disable-web-security");
//                    options.addArguments("--allow-running-insecure-content");
                    options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 6.0; HTC One M9 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36");
//                    options.setExperimentalOption("useAutomationExtension", false);
//                    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                    webDriver = new ChromeDriver(options);
                    break;
                case "Windows":

                    ChromeOptions optionswindow = new ChromeOptions();
//                    String str_proxy_windows = "--proxy-server=socks4://"+InetAddress.getLocalHost().getHostAddress()+":1080";
//                    String str_proxy_windows = "--proxy-server=socks4://127.0.0.1:1080";
//                    optionswindow.addArguments(str_proxy_windows);
                    optionswindow.addArguments("disable-infobars");
                    optionswindow.addArguments("--start-maximized");
                    optionswindow.addArguments("user-data-dir=E:\\Soft\\gg-Copy\\Data\\profile");
//                    optionswindow.addArguments("--headless");

                    try {
                        webDriver = new ChromeDriver(optionswindow);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return webDriver;

    }

}
