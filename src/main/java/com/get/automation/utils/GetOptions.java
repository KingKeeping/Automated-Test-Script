package com.get.automation.utils;

import com.get.automation.config.MyProperties;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;

public class GetOptions{

   static {
       System.setProperty(MyProperties.getProperties().getProperty("browser"),
               MyProperties.getProperties().getProperty("path"));
             }

        public static  ChromeOptions getOption(){
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
        //WebDriver driver=new ChromeDriver(options);
            return options;



    }}
