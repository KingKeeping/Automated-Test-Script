package com.get.automation.tests;

import com.get.automation.config.MyProperties;
import com.get.automation.pages.KhomePage;
import com.get.automation.pages.KloginPage;
import com.get.automation.pages.PlanToolPage;
import com.get.automation.pages.ProjectHomePage;
import com.get.automation.utils.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * 此测试方法为登录kmoke
 */
public class KmokeLoginTest {

    WebDriver driver=WebDriverManager.getDriver();
    KhomePage khomePage=new KhomePage(driver);
    KloginPage kloginPage=new KloginPage(driver);
    ProjectHomePage projectHomePage=new ProjectHomePage(driver);
    PlanToolPage planToolPage=new PlanToolPage(driver);
    List<String> projectName=new ArrayList<>();

    @BeforeClass
    public void klogin(){

        kloginPage.enterUserName(MyProperties.getProperties().getProperty("kuser"));
        kloginPage.enterPwd(MyProperties.getProperties().getProperty("kpwd"));
        kloginPage.clickButton();
        khomePage.clickMenu();

    }

    /**
     * 此测试用例用于验证是否跳转到计划工具
     */
    @Test(priority = 1)
    public void usePlanTool(){
        projectHomePage.clickPlanTool();
        Assert.assertTrue(planToolPage.getText(planToolPage.getSegmentByProject()).contains("按项目")
                ,"成功进入到计划工具页面");


    }

    /**
     * 此测试用于验证项目是否正确
     */
    @Test(priority = 2)
    public void getProject(){
        WebElement parent=planToolPage.getParentElement();
        if(parent!=null){
        List<WebElement> projects = planToolPage.getProjects(parent);
        for(WebElement project : projects){
            String text = planToolPage.getText(project);
            projectName.add(text);
        }
        }
        System.out.println(projectName);
    }


    @AfterClass
    public void klogout(){
        if(driver!=null){
            WebDriverManager.quitDriver(driver);
        }

    }

}
