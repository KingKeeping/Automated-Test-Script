package com.get.automation.tests.multiDimensionalTableTest;
import com.get.automation.config.MyProperties;
import com.get.automation.pages.KhomePage;
import com.get.automation.pages.KloginPage;
import com.get.automation.pages.MultiDimensionalTableHomePage;
import com.get.automation.pages.MultidimensionalTableViewPage;
import com.get.automation.utils.RandomStringGenerator;
import com.get.automation.utils.WebDriverManager;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;


public class MultiDimensionalTableTest {
    private WebDriver driver=WebDriverManager.getDriver();
    private KloginPage klogin=new KloginPage(driver);
    private KhomePage khomePage=new KhomePage(driver);
    private MultiDimensionalTableHomePage homePage=new MultiDimensionalTableHomePage(driver);
    private MultidimensionalTableViewPage viewPage=new MultidimensionalTableViewPage(driver);
    Actions actions=new Actions(driver);
    Random random=new Random();
    //进入多维表首页
    @BeforeSuite
    public void loginKmoke() {
        //向数据库插入15条不同时间维度的的多维表
        // stringListMap = FileDataInserter.insertTestData();
        klogin.enterUserName(MyProperties.properties.getProperty("kuser"));
        klogin.enterPwd(MyProperties.properties.getProperty("kpwd"));
        klogin.clickButton();
        khomePage.clickMultidimensionalTable();
    }



    //点击新建文件
        @Test(priority = 1)
        public void testCreateNewFile() {
            homePage.createTable(actions,"测试新建");
            WebElement alert=driver.findElement(By.cssSelector(".ant-message-notice-content"));
            String message = alert.findElement(By.tagName("span")).getText();
            if(message!=null){
                Assert.assertEquals(message,"重命名成功","重命名失败");
            }else {
                Assert.assertTrue(false);
            }
            waitFor(6000L);
        }



    //点击我创建的按钮，判断文件是否都由我创建
    //折中方法之判断文件是否全为我创建的
    @Test(description = "对文件的判断", priority = 2)
    public void testSearchByName(){
        //点击我创建的按钮，拿到当前所有文件数量，再拿到当前属于我的文件的数量对比
        homePage.click(homePage.getManagedByMeButton());
        List<WebElement> myTables = driver.findElements(By.xpath("//td[contains(text(), '鲜蕾')]"));
        List<WebElement> elements = homePage.getValidElement("我管理的");
        if(!(elements.isEmpty())){
            Assert.assertEquals(elements.size() ,myTables.size(),"有存在不是我创建的文件");
        }
        waitFor(4000L);
    }





        /**
         * 点击我收藏的页面，随机选择一个文件进行操作
         * 如果文件操作为取消收藏，则进行取消收藏操作，反之亦然
         */
            @Test(description = "收藏判断，包括取消收藏或者收藏",priority = 3)
            public void testFavrioteTable(){
                homePage.click(homePage.getManagedByMeButton());
                List<String> type = homePage.
                        favoritesTable(homePage.getValidElement("我管理的"), actions);
                if((type.get(0)).equals("收藏")){
                    //跳转到收藏页面查看是否收藏成功
                    if (!((homePage.getTableNames()).isEmpty())){
                        homePage.click(homePage.getFavoritesButton());
                        Assert.assertTrue((homePage.getTableNames()).contains(type.get(1)),"收藏失败");
                    }
                }else {
                    //跳转到收藏页面，查看收藏中是否还存在此文件
                    if(!((homePage.getTableNames()).isEmpty())){
                        homePage.click(homePage.getFavoritesButton());
                        Assert.assertTrue(!(homePage.getTableNames().contains(type.get(1))),"取消收藏失败");
                    }
                }
                waitFor(6000L);
            }





            //删除操作
            @Test(description = "对文件删除的判断",priority = 4)
            public void testDeleteFile() throws InterruptedException {
                //点击我管理的页面
                homePage.click(homePage.getManagedByMeButton());
                List<WebElement> elements = homePage.getValidElement("我管理的");
                Thread.sleep(5000);
                String tableName = homePage.deleteTable(elements,actions);
                //跳转到回收站，看文件是否到了回收站
                homePage.click(homePage.getRecycleBinButton());
                List<String> tableNames = homePage.getTableNames();
                Assert.assertTrue(tableNames.contains(tableName),"删除操作不成功");
                waitFor(6000L);
            }



        //验证文件还原
        @Test(description = "还原被删除的文件",priority = 5)
        public void testRecycleFile(){
            homePage.click(homePage.getRecycleBinButton());
            List<WebElement> elements = homePage.getValidElement("回收站");
            String s = homePage.recycleFile(elements, actions);
            //判断当前页面是否还存在这个文件
            if(!(homePage.getTableNames().isEmpty())) {
                Assert.assertTrue(!(homePage.getTableNames()).contains(s), "文件还原操作不成功");
            }
            waitFor(5000L);
        }



        //验证文件被彻底删除
        @Test(dependsOnMethods = "testDeleteFile",priority = 6)
        public void testCleanFiles(){
            homePage.click(homePage.getRecycleBinButton());
            List<WebElement> elements = homePage.getValidElement("回收站");
            if(!(elements.isEmpty())){
                String s = homePage.permanentlyDeleteFile(elements, actions);
                //判断当前页面是否还存在这个文件
                if(!(homePage.getTableNames().isEmpty())) {
                    Assert.assertTrue(!(homePage.getTableNames()).contains(s), "文件彻底删除操作不成功");
                }
            }
            waitFor(5000L);

        }

    //对文件重命名判断
    @Test(description = "重命名文件",priority = 7)
    public void testReName(){
        homePage.click(homePage.getManagedByMeButton());
        String newName= RandomStringGenerator.generateRandomString(3);
        List<WebElement> elements = homePage.getValidElement("我管理的");
        String tableName=homePage.reNameTable(newName,elements,actions);
        //进行判断,拿到当前页面的文件名称，判断有没有包含当前文件名的文件
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<String> tableNames = homePage.getTableNames();
        if(!tableNames.isEmpty()){
            System.out.println("重命名后的文件名为："+tableNames);
            Assert.assertTrue(tableNames.contains(tableName+newName),"重命名失败");
        }else {
            Assert.fail("文件列表没有返回");
        }

    }

        //搜索
        @Test(description = "验证搜索",priority = 8)
        public void testSearch(){
            //随机选择一个页面点击，拿到当前页面的文本值，选择一个文本放入搜索框，再查看返回的值是否包含搜索的文本
            List<WebElement> btn=new ArrayList<>();
            btn.add(homePage.getRecentAccessButton());
            btn.add(homePage.getManagedByMeButton());
            //btn.add(homePage.getFavoritesButton());
           // btn.add(homePage.getRecycleBinButton());
            WebElement randomBtn = btn.get(random.nextInt(btn.size()));
            randomBtn.click();
            //拿到当前页面的所有文件名,并随机选则一个文件名
            List<String> tableNames= homePage.getTableNames();
            if(!(tableNames.isEmpty())){
                String tableName = tableNames.get(random.nextInt(tableNames.size()));
                homePage.searchByTableName(tableName);
                //再次拿到当前页面文件名，进行判断
                List<String> tableNames2=homePage.getTableNames();
                if(tableNames2.contains(tableName)){
                    Assert.assertTrue(true);
                }
            }else {
                Assert.assertTrue(false);
            }
            waitFor(3000L);
        }


        public void waitFor(Long seconds){
            try {
                Thread.sleep(seconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }




        @AfterClass
        public void tearDown() {
            if (driver != null) {
               WebDriverManager.quitDriver(driver);
            }
        }
}







