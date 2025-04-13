package com.testing.testai;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v107.page.Page;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

import java.time.Duration;


import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    @Test
    public void aiLogin(){

        //隐式等待
        Duration timewait=Duration.ofSeconds(10);

        System.setProperty("webdriver.chrome.driver",
                "C:\\dev2\\ChromeDriver132.0.6834.111\\chromedriver-win64\\chromedriver.exe");


        // 配置 Chrome 选项
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled"); // 禁用自动化标志
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36"); // 设置用户代理
        options.addArguments("--start-maximized"); // 最大化窗口
        options.addArguments("--user-data-dir=C:\\Users\\鲜蕾\\AppData\\Local\\Google\\Chrome\\User Data1"); // 设置用户数据目录
        options.addArguments("--profile-directory=Default"); // 设置配置文件目录（默认为 Default）
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver=new ChromeDriver(options);


        // 模拟人类行为：随机延迟
        Random random = new Random();
        try {
            //隐式等待
            driver.manage().timeouts().implicitlyWait(timewait);
            //获取地址
            driver.get("https://www.vsaker.com/");

            Thread.sleep(random.nextInt(3000) + 1000); // 随机延迟 1-4 秒



        //元素定位（在此用css)，点击
        driver.findElement(By.cssSelector(".un-login.d-flex-c-r")).click();
        //表单切换
        driver.findElement(By.xpath("//*[@id=\"AIHome\"]/div[6]/div/div[2]/div/div/p[1]/span[3]")).click();
        //输入手机号
        driver.findElement(By.xpath("//*[@id=\"AIHome\"]/div[6]/div/div[2]/div/div/form/div[1]/div/div/input"))
                .sendKeys("17361379708");
        //输入密码
        driver.findElement(By.xpath("//*[@id=\"AIHome\"]/div[6]/div/div[2]/div/div/form/div[2]/div/div/input"))
                .sendKeys("xian200188");
        //同意协议
        driver.findElement(By.xpath("//*[@id=\"AIHome\"]/div[6]/div/div[2]/div/div/div/label/span/span")).click();
        //登录
        driver.findElement(By.xpath("//*[@id=\"AIHome\"]/div[6]/div/div[2]/div/div/button/span")).click();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            driver.quit();
        }

}}
