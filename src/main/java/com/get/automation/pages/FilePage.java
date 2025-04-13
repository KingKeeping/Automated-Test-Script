package com.get.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class FilePage {
    WebDriver driver;
    public FilePage(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.driver = driver;
    }
    @FindBy(xpath = "重命名按钮")
    private WebElement reFileNameBtn;
    @FindBy(xpath = "删除按钮")
    private WebElement deteleFileBtn;
    @FindBy(xpath = "收藏按钮")
    private WebElement favoriteFileBtn;


}
