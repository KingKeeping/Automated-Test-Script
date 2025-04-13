package com.get.automation.pages;

import com.get.automation.constants.KhomePageConstant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class KhomePage {
    WebDriver driver;
    public KhomePage(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = KhomePageConstant.menu)
    private WebElement menu;
    //多维表按钮对象
    @FindBy(xpath = KhomePageConstant.Multidimensional_Table)
    private WebElement Multidimensional_Table;
    //退出
    @FindBy(xpath = "退出")
    private WebElement LogBack;

    //点击进入项目菜单
    public void clickMenu() {
        menu.click();
    }

    //点击进入多维表
    public void clickMultidimensionalTable() {
        Multidimensional_Table.click();
    }

    //退出kmoke
    public void clickLogBack() {
        LogBack.click();
    }
}
