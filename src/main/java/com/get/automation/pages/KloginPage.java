package com.get.automation.pages;

import com.get.automation.constants.KloginConstant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class KloginPage {


    WebDriver driver;
    public KloginPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css= KloginConstant.User)
    private WebElement user;

    @FindBy(xpath= KloginConstant.Password)
    private WebElement pwd;

    @FindBy(xpath= KloginConstant.LoginButton)
    private WebElement button;

    //输入用户名
    public void enterUserName(String userName) {
        if(this.user!=null){
            this.user.sendKeys(userName);
        }else {
            System.out.println("User is null");
        }

    }

    //输入密码
    public void enterPwd(String pwd) {
        if(this.pwd!=null){
            this.pwd.sendKeys(pwd);
        }else {
            System.out.println("Pwd is null");
        }
    }

    //点击登录按钮
    public void clickButton() {
        button.click();
    }


}
