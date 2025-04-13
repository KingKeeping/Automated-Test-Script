package com.testing.testai;

import com.get.automation.constants.KhomePageConstant;
import com.get.automation.constants.KloginConstant;
import com.get.automation.constants.PlanToolConstant;
import com.get.automation.constants.ProjectHomeConstant;
import com.get.automation.utils.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

public class TestKmoke {

    String xpath;

    @Test
    public void kmokeLogin(){

        //隐式等待
        Duration timewait=Duration.ofSeconds(20);

        System.setProperty("webdriver.chrome.driver",
                "C:\\dev2\\ChromeDriver132.0.6834.111\\chromedriver-win64\\chromedriver.exe");

        // 配置 Chrome 选项
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled"); // 禁用自动化标志
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36"); // 设置用户代理
        options.addArguments("--start-maximized"); // 最大化窗口
        options.addArguments("--user-data-dir=C:\\Users\\鲜蕾\\AppData\\Local\\Google\\Chrome\\User Data1"); // 设置用户数据目录
        options.addArguments("--profile-directory=Default"); // 设置配置文件目录（默认为 Default）
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver=new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        Actions actions=new Actions(driver);
        // 模拟人类行为：随机延迟
        Random random = new Random();
        try {
            //隐式等待
            driver.manage().timeouts().implicitlyWait(timewait);
            //获取地址
            driver.get("https://kmoke-test.ver.cn:7072/login");

            //元素定位（在此用css)，点击
            driver.findElement(By.cssSelector(KloginConstant.User)).sendKeys("xianlei");

            driver.findElement(By.xpath(KloginConstant.Password)).sendKeys("123456");
            driver.findElement(By.xpath(KloginConstant.LoginButton)).click();

            driver.findElement(By.xpath("//*[@id=\"arearight_667\"]/div[1]/div[2]/div[18]")).click();
            //点击我管理的分组
            driver.findElement(By.xpath("//span[contains(text(),'我管理的')]")).click();
            List<String> tableName=new ArrayList<>();


            //拿到下面的子结点
            List<WebElement> elements = driver.findElements(By.cssSelector(".name-txt"));
            elements.forEach(webElement -> {
                WebElement element = webElement.findElement(By.cssSelector(".name-txt-inner"));
                String name=element.getText();
                if(name!=null){
                    tableName.add(name);
                }

            });


            //在这里做一个判断，过滤掉时间标签
            List<WebElement> validtr=new ArrayList<>();
            List<String> timename=new ArrayList(Arrays.asList("2_-1","2_-2","2_-3"));
            // WebElement parent = driver.findElement(By.cssSelector(".ant-table-tbody"));
            List<WebElement> ch = driver.findElements(By.cssSelector(".ant-table-row.ant-table-row-level-0"));
            ch.forEach(webElement -> {
                String attribute = webElement.getAttribute("data-row-key");
                if(!timename.contains(attribute)){
                    validtr.add(webElement);
                }
            });
            System.out.println("原值为"+ch.size());
            System.out.println("过滤后的值为"+validtr.size());

            //折中方法之判断文件是否全为我创建的
            List<WebElement> myTables = driver.findElements(By.xpath("//td[contains(text(), '鲜蕾')]"));
            System.out.println(myTables.size());
            if(myTables.size()==validtr.size()){
                System.out.println("判断通过");
            }else {
                System.out.println("判断不通过");
            }

            //随机寻找一个悬浮
            WebElement svg=validtr.get(random.nextInt(validtr.size())).findElement(By.cssSelector(".action-icon.anticon.anticon-more"));
            actions.moveToElement(svg);
            actions.perform();
            Thread.sleep(random.nextInt(3000) + 1000);
            //选择重命名
            WebElement element = driver.findElement(By.cssSelector(".action-menu"));
            List<WebElement> selectform = element.findElements(By.cssSelector(".action-item"));
            selectform.forEach(webElement -> {
                System.out.println(webElement.getText());
            });
            //选择重命名按钮
            selectform.get(0).click();
            WebElement input = driver.findElement(By.cssSelector("[placeholder=请输入名称]"));
            input.clear();
            input.sendKeys("111");
            Thread.sleep(5000);
            //点击确定
            WebElement btn=driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div[2]/div[3]/div/button[2]/span"));
           /* JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", btn);
            */
            actions.click(btn).perform();
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            WebDriverManager.quitDriver(driver);
        }

    }
}


