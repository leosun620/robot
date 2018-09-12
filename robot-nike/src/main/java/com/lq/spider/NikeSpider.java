package com.lq.spider;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liuqiang on 2018/6/21.
 */
public class NikeSpider {

    private WebDriver chromeDriver;
    private String userName="tianshi139803@126.com";
    private String password = "liu123456L";
    private String productURL = "https://www.nike.com/cn/launch/t/sb-dunk-low-ride-life/";

    private void takesScreenshot(WebDriver driver,String name){
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("D:\\temp\\"+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void inputPassword(WebDriverWait wait,WebDriver driver){
        //在******中填你的用户名
        WebElement userNameElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-componentname='emailAddress']")));
        System.out.println(userNameElement.toString());
        userNameElement.sendKeys(userName);
        WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-componentname='password']")));
        //在*******填你密码
        passwordElement.sendKeys(password);

        //模拟点击登录按钮
        driver.findElement(By.xpath("//input[@value='登录']")).click();
    }

    public void orderProduct(String productURL){
        System.out.println("start:"+new Date());
        WebDriver driver = getChromeDriver();
        takesScreenshot(driver,"pre.png");
        driver.get(productURL);
        takesScreenshot(driver,"product.png");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.xpath("//span[@class='va-sm-m']")).click();
        List<WebElement> sizeElements = driver.findElements(By.xpath("//li[@data-qa='size-available']"));
        for(WebElement sizeEle : sizeElements){
            System.out.println(sizeEle.getText());

        }
        takesScreenshot(driver,"product-size.png");
        sizeElements.get(1).findElement(By.tagName("button")).click();

        //driver.findElement(By.xpath("//button[@data-qa='feed-buy-cta']")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@data-qa='add-to-jcart']")).click();
        takesScreenshot(driver,"product-addrss.png");

        System.out.println("end:"+new Date());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Set<Cookie> login(){
        WebDriver driver = getChromeDriver();
        driver.get("https://www.nike.com/cn/zh_cn/");
        driver.manage().window().maximize();
        takesScreenshot(driver,"index.png");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(By.className("login-text")));
        System.out.println(webElement.getText());
        webElement.click();
        takesScreenshot(driver,"login.png");

        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'使用电子邮件登录')]"));
        element.click();

        try{
            inputPassword(wait,driver);
        }catch (Exception e){
            e.printStackTrace();
        }
        WebElement userElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@js-hook='username']")));
        if(userElement!=null){
            System.out.println("登陆成功:"+userElement.getText());
        }
        takesScreenshot(driver,"login-ed.png");

        //获取cookie信息
        return driver.manage().getCookies();

    }

    private WebDriver getChromeDriver(){
        if(chromeDriver != null){
            return chromeDriver;
        }
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        //options.addArguments("user-data-dir=C:\\Users\\liuqiang\\AppData\\Local\\Google\\Chrome\\User Data");
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability("headless",true);
        dcaps.setCapability("--gpu",true);
        dcaps.setCapability("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        chromeDriver = new ChromeDriver(options);
        return chromeDriver;
    }

    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver","D:\\资料\\爬虫\\chromedriver.exe");
        NikeSpider spider = new NikeSpider();

        try{
            spider.login();
            spider.orderProduct("https://www.nike.com/cn/launch/t/sb-dunk-low-ride-life/");
        }catch (Exception e){
            e.printStackTrace();
        }

        spider.getChromeDriver().close();
        spider.getChromeDriver().quit();
    }
}
