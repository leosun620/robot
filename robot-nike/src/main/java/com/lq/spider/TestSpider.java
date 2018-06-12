package com.lq.spider;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Set;

/**
 * Created by liuqiang on 2018/6/7.
 */
public class TestSpider implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    //用来存储cookie信息
    private Set<Cookie> cookies;
    @Override
    public void process(Page page) {
        //获取用户的id
        List<String> uls = page.getHtml().xpath("//table[@class='huiy']").all();
        for(String ul : uls) {
            System.out.println(ul);

        }
        page.putField("", uls);
        //获取用户的详细信息

    }

    //使用 selenium 来模拟用户的登录获取cookie信息
    public void login()
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        //WebDriver driver = new ChromeDriver(options);
        WebDriver driver = SpiderDemo.getPhantomJSDriver();
        driver.get("http://11.33.186.42:8008/signInfo/faces/login.jsp");

        //在******中填你的用户名
        WebElement element = driver.findElement(By.id("loginform:staffId"));
        System.out.println(element.toString());
        element.sendKeys("liuqiang10");

        driver.findElement(By.id("loginform:password")).clear();
        //在*******填你密码
        driver.findElement(By.id("loginform:password")).sendKeys("password");

        //模拟点击登录按钮
        driver.findElement(By.id("loginform:loginBtn")).click();
        driver.get("http://11.33.186.42:8008/signInfo/faces/check/person_detail.jsp");
        driver.findElement(By.id("form1:look_detail")).click();
        //获取cookie信息
        cookies = driver.manage().getCookies();
        driver.close();
    }

    @Override
    public Site getSite() {

        //将获取到的cookie信息添加到webmagic中
        for (Cookie cookie : cookies) {
            site.addCookie(cookie.getName().toString(),cookie.getValue().toString());
        }

        return site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1");
    }

    public static void main(String[] args){
        TestSpider miai = new TestSpider();
        System.setProperty("webdriver.chrome.driver","D:\\资料\\爬虫\\chromedriver.exe");
        //调用selenium，进行模拟登录
        miai.login();
        Spider.create(miai)
                .addUrl("http://11.33.186.42:8008/signInfo/faces/check/person_detail.jsp")
                .run();
    }
}
