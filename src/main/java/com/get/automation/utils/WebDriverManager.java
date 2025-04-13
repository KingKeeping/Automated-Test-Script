package com.get.automation.utils;
import com.get.automation.config.MyProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 异常处理
 * 日志记录
 *
 */


public class WebDriverManager {

    private WebDriverManager(){
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


        // 使用 ThreadLocal 存储 WebDriver 实例
        private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

        // 初始化 WebDriver
        public static void initializeDriver() {
            WebDriver driver = new ChromeDriver(GetOptions.getOption());
            driver.get(MyProperties.properties.getProperty("kbaseurl"));
            driverThreadLocal.set(driver); // 将 WebDriver 实例绑定到当前线程
        }


        // 获取当前线程的 WebDriver 实例
        public static WebDriver getDriver() {
            WebDriver driver = driverThreadLocal.get();
            if (driver == null) {
                initializeDriver();
                driver = driverThreadLocal.get();
            }
            return driver;
        }


        // 关闭 WebDriver 并清理 ThreadLocal
        public static void quitDriver(WebDriver driver) {
           // WebDriver driver = driverThreadLocal.get();
            if (driver != null) {
                driver.quit();
                driverThreadLocal.remove(); // 清理 ThreadLocal
            }
        }
    }




