package com.get.automation.utils;

import com.get.automation.pages.MultiDimensionalTableHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class WebElementUtils {
    public static WebDriverWait wait;

    public static List<WebElement> waitForElementsWithRetry(By by, int maxRetries, WebDriver driver) {
        Logger logger= LoggerFactory.getLogger(MultiDimensionalTableHomePage.class);
        wait= new WebDriverWait(driver, Duration.ofSeconds(20));
        int retries = 0;
        while (retries < maxRetries) {
            try {
                return driver.findElements(by);
            } catch (StaleElementReferenceException e) {
                logger.warn("元素异常，重试第 {} 次...", retries + 1);
                retries++;
                try {
                    Thread.sleep(1000); // 等待 1 秒后重试
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        throw new StaleElementReferenceException("元素加载依然异常 after " + maxRetries + " retries");
    }

    public static WebElement waitForElementWithRetry(By by, int maxRetries, WebDriver driver) {
        Logger logger= LoggerFactory.getLogger(MultiDimensionalTableHomePage.class);
        int retries = 0;
        while (retries < maxRetries) {
            try {
                return driver.findElement(by);
            } catch (StaleElementReferenceException e) {
                logger.warn("元素异常，重试第 {} 次...", retries + 1);
                retries++;
                try {
                    Thread.sleep(1000); // 等待 1 秒后重试
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        throw new StaleElementReferenceException("元素加载依然异常 after " + maxRetries + " retries");
    }
}
