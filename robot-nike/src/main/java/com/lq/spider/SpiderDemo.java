package com.lq.spider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * Created by liuqiang on 2018/6/7.
 */
public class SpiderDemo implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    //用来存储cookie信息
    private Set<Cookie> cookies;

    @Override
    public void process(Page page) {
        //获取用户的id
        //page.putField("", page.getHtml().xpath("//div[@class='page-index'"));
        page.addTargetRequests(page.getHtml().links().regex("https://www.nike.com/cn/t.*").all());
        //获取用户的详细信息
        //List<String> information = page.getHtml().xpath("//div[@class='page-index'").all();
        //page.putField("information = ", information);
        page.putField("production",page.getHtml().xpath("//h2[@class='fs16-sm pb1-sm']"));
        page.putField("productionE",page.getHtml().xpath("//h1[@class='fs26-sm fs28-lg']"));
        page.putField("price",page.getHtml().xpath("//div[@class='text-color-black']"));
        page.putField("color",page.getHtml().xpath("//img[@class='bg-medium-grey']"));
        page.putField("additem",page.getHtml().xpath("//div[@class='buying-tools-container']"));
    }

    private void takesScreenshot(WebDriver driver,String name){
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("D:\\temp\\"+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //使用 selenium 来模拟用户的登录获取cookie信息
    public void login()
    {

        //WebDriver driver = getPhantomJSDriver();
        WebDriver driver = getChromeDriver();
        driver.get("https://www.nike.com/cn/zh_cn/");
        driver.manage().window().maximize();
        takesScreenshot(driver,"index.png");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(By.className("login-text")));
        System.out.println(webElement.getText());
        webElement.click();
        takesScreenshot(driver,"login.png");

        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'使用电子邮件登录')]"));
        element.click();

        try{
            input(wait,driver);
        }catch (Exception e){
            e.printStackTrace();
        }
        takesScreenshot(driver,"login-ed.png");

        //获取cookie信息
        cookies = driver.manage().getCookies();
        driver.close();

    }

    private  void input(WebDriverWait wait,WebDriver driver){
        //在******中填你的用户名
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-componentname='emailAddress']")));
        WebElement userNameElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-componentname='emailAddress']")));
        System.out.println(userNameElement.toString());
        userNameElement.sendKeys("tianshi139803@126.com");
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-componentname='password']")));
        WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-componentname='password']")));
        //在*******填你密码
        passwordElement.sendKeys("liu123456L");

        //模拟点击登录按钮
        driver.findElement(By.xpath("//input[@value='登录']")).click();
    }
    @Override
    public Site getSite() {

        //将获取到的cookie信息添加到webmagic中
        for (Cookie cookie : cookies) {
            site.addCookie(cookie.getName().toString(),cookie.getValue().toString());
        }
        site.addHeader("content-type","application/json");
        return site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
    }

    public static WebDriver getChromeDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability("headless",true);
        dcaps.setCapability("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    public static PhantomJSDriver getPhantomJSDriver(){
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        dcaps.setBrowserName("Chrome");
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        String [] phantomJsArgs = {"--debug=true"};
        dcaps.setCapability(
                PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
                phantomJsArgs);
        //设置参数
        //dcaps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        dcaps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"D:\\资料\\爬虫\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");

        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        return  driver;
    }
    public static void main(String[] args){
        SpiderDemo miai = new SpiderDemo();
        System.setProperty("webdriver.chrome.driver","D:\\资料\\爬虫\\chromedriver.exe");
        //调用selenium，进行模拟登录
        miai.login();
        Spider.create(miai)
                .addUrl("https://www.nike.com/cn/launch/t/air-jordan-2-retro-melo/")
                .run();
    }
}
