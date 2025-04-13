package com.get.automation.pages;

import com.get.automation.constants.MultidimensionalTableView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class MultidimensionalTableViewPage {
    WebDriver driver;
    public  MultidimensionalTableViewPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    //重命名
    @FindBy(xpath = MultidimensionalTableView.reName)
    private WebElement reNameBtn;

    //返回多维表首页
    @FindBy(xpath = MultidimensionalTableView.backHomePage)
    private WebElement backHomeBtn;

    //增加视图
    public WebElement getAddViewBtn() {
        return addViewBtn;
    }

    //创建其它视图按钮
    @FindBy(xpath = "")
    private WebElement addViewBtn;

    //点击操作
    public void click(WebElement element) {
        element.click();
    }



    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getReNameBtn() {
        return reNameBtn;
    }

    public WebElement getBackHomeBtn() {
        return backHomeBtn;
    }
}
