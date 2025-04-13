package com.get.automation.pages;

import com.get.automation.constants.ProjectHomeConstant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class ProjectHomePage {
    WebDriver driver;
    public ProjectHomePage(WebDriver driver) {

        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath=ProjectHomeConstant.PlanTool)
    private WebElement planToolButton;

    @FindBy(xpath = ProjectHomeConstant.myProject)
    private WebElement myProjectButton;

    //点击我的项目按钮，跳转到我的项目页面
    public void clickMyProject() {
        myProjectButton.click();
    }
    //点击计划工具按钮，跳转到计划工具页面
    public void clickPlanTool() {
        planToolButton.click();
    }




}
