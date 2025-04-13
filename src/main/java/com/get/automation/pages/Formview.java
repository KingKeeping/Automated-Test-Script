package com.get.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class Formview {
    WebDriver driver;
    public Formview(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    //编辑表单按钮
    @FindBy(xpath = "")
    private WebElement editformBtn;
    //填写表单按钮
    @FindBy(xpath = " ")
    private WebElement fillFormBtn;
    //左功能栏
    @FindBy(xpath = " ")
    private WebElement leftFunctionBar;

    //右功能栏
    @FindBy(xpath = " ")
    private WebElement rightFunctionBar;


    public WebElement getEditformBtn() {
        return editformBtn;
    }

    public WebElement getFillFormBtn() {
        return fillFormBtn;
    }

    public WebElement getLeftFunctionBar() {
        return leftFunctionBar;
    }

    public WebElement getRightFunctionBar() {
        return rightFunctionBar;
    }
}
