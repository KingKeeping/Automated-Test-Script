package com.get.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class TableView {
    WebDriver driver;
    public TableView(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //字段管理按钮
    @FindBy(xpath = " ")
    private WebElement fieldManageBtn;
    //添加字段按钮
    @FindBy(xpath = " ")
    private WebElement addFieldBtn;
    //添加字段输入框
    private WebElement inputFieldBtn;
    //确认按钮
    private WebElement enterBtn;

    public WebElement getEnterBtn() {
        return enterBtn;
    }

    //点击操作
    public void click(WebElement element){
        fieldManageBtn.click();
    }

    //输入文本操作
    public void input(WebElement element,String value){
        //先清空之前的文本
        element.clear();
        element.sendKeys(value);
    }

    public WebElement getFieldManageBtn() {
        return fieldManageBtn;
    }

    public WebElement getInputFieldBtn() {
        return inputFieldBtn;
    }

    public WebElement getAddFieldBtn() {
        return addFieldBtn;
    }
}
