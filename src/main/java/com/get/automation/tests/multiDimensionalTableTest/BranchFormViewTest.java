package com.get.automation.tests.multiDimensionalTableTest;

import com.get.automation.config.MyProperties;
import com.get.automation.pages.*;
import com.get.automation.utils.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * 此测试类属于多维表表单视图分点测试
 */
public class BranchFormViewTest {
    WebDriver driver= WebDriverManager.getDriver();
    KloginPage login=new KloginPage(driver);
    KhomePage home=new KhomePage(driver);
    TableView tableView=new TableView(driver);
    MultidimensionalTableViewPage viewPage=new MultidimensionalTableViewPage(driver);
    MultiDimensionalTableHomePage mutiTableHome=new MultiDimensionalTableHomePage(driver);
    Actions actions=new Actions(driver);

    /**
     * 先创建一个多维表和表格视图，再创建一个表单视图
     *
     */
    //登录进入多维表页面
    @BeforeClass
    public void setUp() {
        login.enterUserName(MyProperties.properties.getProperty("kuser"));
        login.enterPwd(MyProperties.properties.getProperty("kpwd"));
        home.clickMultidimensionalTable();
    }


    @Test
    public void createNewFile(){
        //点击创建多维表按钮创建一个多维表
        mutiTableHome.click(mutiTableHome.getCreateBtn());
        //进入表格视图
        //点击字段管理按钮，输入一个文本
        tableView.click(tableView.getFieldManageBtn());
        //点击添加字段
        tableView.click(tableView.getAddFieldBtn());
        String fieldName="111";
        //输入文本点击确认，这里的文本考虑是否有外界提供
        tableView.input(tableView.getInputFieldBtn(),fieldName);
        tableView.click(tableView.getEnterBtn());
        //拿到当前字段管理下的所有元素，查看字段有没有添加成功
        List<WebElement> fieldList=driver.findElements(By.xpath(""));
        if(fieldList==null){
            Assert.fail("所有字段未显示");
        }else {
            Assert.assertTrue(fieldList.contains(fieldName),"字段添加失败");
        }

    }


    /**
     * 在表格视图增添一个新的字段后，查看表单视图的选项是否是表格视图所有的字段
     * 在表单视图中增加一个字段，看表格视图的字段管理中是否也同时增加了
     */
    @Test
    public void addFieldTest(){
        //点击添加其他视图按钮
        viewPage.click(viewPage.getAddViewBtn());
        //选择需要添加的视图类型（表单视图）
        //这里假设表单选项的index是0
        WebElement formview=driver.findElements(By.xpath("")).get(0);
        actions.click(formview);
        //跳转到表单视图后，进行验证表单视图的主题是否对应表格视图的字段



    }

    /**
     * 针对被添加到填写项的字段，分别进行隐藏，必填，复制、删除操作
     * 其中：1.隐藏后的填选框会被移至可选题目下方
     *      2.复制：会复制当前题目到当前题目的下方
     *      3.删除：删除当前字段，此操作会同步到其他视图
     *
     */
    @Test
    public void filedOperationTest(){


    }


    @AfterClass(description = "测试结束后删除整个多维表")
    public void deleteMutiDimensionalTable(){


    }

}
