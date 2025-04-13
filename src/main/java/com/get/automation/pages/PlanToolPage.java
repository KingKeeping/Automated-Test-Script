package com.get.automation.pages;

import com.get.automation.constants.PlanToolConstant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class PlanToolPage {
    WebDriver driver;
    public PlanToolPage(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath=PlanToolConstant.segmentByProject)
    private WebElement segmentByProject;

    @FindBy(xpath = PlanToolConstant.getSegmentByNote)
    private WebElement getSegmentByNote;

    @FindBy(xpath = PlanToolConstant.parentElement)
    private WebElement parentElement;


    public WebElement getSegmentByProject() {
        return segmentByProject;
    }

    public WebElement getGetSegmentByNote() {
        return getSegmentByNote;
    }

    public WebElement getParentElement() {
        return  parentElement ;
    }




    //拿到子节点操作
    public List<WebElement> getProjects(WebElement parent) {
        return parent.findElements(By.cssSelector(".name"));
    }

    //拿到项目文本内容
    public String getText(WebElement element) {
        return element.getText();
    }








}
