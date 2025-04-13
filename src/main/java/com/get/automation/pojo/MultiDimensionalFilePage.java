package com.get.automation.pojo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * 针对首页多维表列表下的文件
 */
public class MultiDimensionalFilePage {
    private WebDriver driver;

    @FindBy(css = ".file-title input")
    WebElement titleInput;
    @FindBy(css = ".return-home")
    WebElement returnBtn;

    public MultiDimensionalFilePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void renameFile(String newName) {
        titleInput.sendKeys(Keys.CONTROL + "a");
        titleInput.sendKeys(newName + Keys.ENTER);
        waitForSave();
    }

    public void returnToHome() {
        returnBtn.click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/home"));
    }

    public boolean isOnFilePage() {
        return driver.getCurrentUrl().contains("/file/");
    }

    private void waitForSave() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".saving")));
    }
}
