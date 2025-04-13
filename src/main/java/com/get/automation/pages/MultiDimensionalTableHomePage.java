package com.get.automation.pages;
import com.get.automation.constants.MultiDimensionalTableConstant;
import com.get.automation.utils.WebElementUtils;
import org.bouncycastle.cms.jcajce.JcaX509CertSelectorConverter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.time.Duration;
import java.util.*;

public class MultiDimensionalTableHomePage {
        Logger logger= LoggerFactory.getLogger(MultiDimensionalTableHomePage.class);
        private WebDriver driver;
        WebDriverWait wait;
        // 构造函数
        public MultiDimensionalTableHomePage(WebDriver driver) {
                this.driver = driver;
                this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                PageFactory.initElements(driver, this);
        }

        // 页面元素
        //创建多维表
        @FindBy(xpath = MultiDimensionalTableConstant.Create_New_Multidimensional_Table)
        private WebElement createBtn;

        @FindBy(xpath = MultiDimensionalTableConstant.SEARCH_BUTTON_XPATH)
        private WebElement searchButton;

        @FindBy(xpath = MultiDimensionalTableConstant.RECENT_ACCESS_BUTTON_XPATH)
        private WebElement recentAccessButton;

        @FindBy(xpath =MultiDimensionalTableConstant.MANAGED_BY_ME_BUTTON_XPATH)
        private WebElement managedByMeButton;

        @FindBy(xpath = MultiDimensionalTableConstant.FAVORITES_BUTTON_XPATH)
        private WebElement favoritesButton;

        @FindBy(xpath = MultiDimensionalTableConstant.RECYCLE_BIN_BUTTON_XPATH)
        private WebElement recycleBinButton;



        public WebElement getCreateBtn() {
                return createBtn;
        }

        public WebElement getSearchButton() {
                return searchButton;
        }

        public WebElement getRecentAccessButton() {
                return recentAccessButton;
        }

        public WebElement getManagedByMeButton() {
                return managedByMeButton;
        }

        public WebElement getFavoritesButton() {
                return favoritesButton;
        }

        public WebElement getRecycleBinButton() {
                return recycleBinButton;
        }


        //点击操作
        public void click(WebElement element) {
               // element.click();
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
        }





        //拿到当前页文件名称,所有页面均适用
        public List<String> getTableNames(){
                try{
                        List<String> tableName=new ArrayList<>();
                        //拿到下面的子结点，这里是拿到文件名称
                        List<WebElement> elements=WebElementUtils
                                .waitForElementsWithRetry(By.cssSelector(".name-txt"),5,driver);
                        //List<WebElement> elements = driver.findElements(By.cssSelector(".name-txt"));
                        elements.forEach(webElement -> {
                                WebElement element= WebElementUtils
                                        .waitForElementWithRetry((By.cssSelector(".name-txt-inner")),5,driver);
                                //WebElement element = webElement.findElement(By.cssSelector(".name-txt-inner"));
                                String name=element.getText();
                                if(name!=null){
                                        tableName.add(name);
                                }
                        });
                        return tableName;
                }catch (Exception e){
                        logger.error("获取文件名失败",e.getMessage());
                }
                throw new RuntimeException("获取文件名操作异常");
        }




        //拿到有效元素
        public List<WebElement> getValidElement(String type){
                List<String> timename;
                switch (type){
                        case "我管理的":
                                timename=new ArrayList(Arrays.asList("2_-1","2_-2","2_-3"));
                                return  getElements(timename);
                        case "最近访问":
                                timename=new ArrayList(Arrays.asList("1_-1","1_-2","1_-3"));
                                return getElements(timename);
                        case "回收站":
                                timename=new ArrayList(Arrays.asList("5_-1","5_-2","5_-3"));
                                return getElements(timename);
                        case "收藏":
                                return driver.findElements(By.cssSelector(".ant-table-row.ant-table-row-level-0"));
                }
                return null;
        }


        public List<WebElement> getElements(List<String> timename) {
                List<WebElement> validtr=new ArrayList<>();
                //List<String>
                //先等待元素出现
                //List<WebElement> ch = driver.findElements(By.cssSelector(".ant-table-row.ant-table-row-level-0"));
                List<WebElement> ch=null;
                try {
                        ch = wait.until(ExpectedConditions
                                .visibilityOfAllElementsLocatedBy(By.cssSelector(".ant-table-row.ant-table-row-level-0")));
                }catch (StaleElementReferenceException e){
                        ch = wait.until(ExpectedConditions
                                .visibilityOfAllElementsLocatedBy(By.cssSelector(".ant-table-row.ant-table-row-level-0")));
                }

                ch.forEach(webElement -> {
                        String attribute = webElement.getAttribute("data-row-key");
                        if(!timename.contains(attribute)){
                                validtr.add(webElement);
                        }
                });
                return validtr;
        }



        //创建多维表功能
        public void createTable(Actions actions){
                try {
                        createBtn.click();
                        //点击确定按钮
                        WebElement parentbtn = WebElementUtils
                                .waitForElementWithRetry(By.cssSelector(".ant-modal-confirm-btns"), 4, driver);
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                                 executor.executeScript("arguments[0].click();"
                                         , parentbtn.findElement(By.cssSelector(".ant-btn.ant-btn-primary")));

                }catch (Exception e){
                        logger.error("新建多维表异常");
                        throw new RuntimeException("新建多维表失败",e);
                }

        }


        //重命名功能
        public String reNameTable(String newName, List<WebElement> elements, Actions actions){
                //随机选择一个文件，进行重命名操作
                Random random=new Random();
                try{
                        WebElement table = elements.get(random.nextInt(elements.size()));
                        WebElement svg=table.findElement(By.cssSelector(".action-icon.anticon.anticon-more"));
                        actions.moveToElement(svg).perform();
                        // 改进后的表单元素获取
                        List<WebElement> selectform = WebElementUtils
                                .waitForElementsWithRetry(By.cssSelector(".action-item"),5,driver);
                        if (selectform.isEmpty()) {
                                logger.error("未找到操作菜单项");
                        }
                        WebElement clickbtn = selectform.get(0);
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                        executor.executeScript("arguments[0].click();", clickbtn);
                        //文本输入框
                        WebElement input = driver.findElement(By.xpath("//input[@placeholder='请输入名称']"));
                        deleteFileAndInput(input,actions,newName);
                        // 确认操作
                        clickConfirmButton();
                        return table.findElement(By.cssSelector(".name-txt-inner")).getText();
                }catch (Exception e){
                        logger.error("重命名操作失败", e);
                        throw new RuntimeException("重命名操作异常", e);
                }

                
        }

        private void clickConfirmButton() {
                try {
                        WebElement checkbox = WebElementUtils
                                .waitForElementWithRetry(By.xpath("//body//div//button[2]"), 10,driver);
                        checkbox.click();
                        logger.info("重命名操作成功");
                } catch (Exception e) {
                        logger.error("确认按钮点击失败", e);
                        // 尝试其他确认方式
                        WebElement confirm = driver.findElement(By.xpath("//body//div//button[2]"));
                        confirm.click();
                }
        }

        private void deleteFileAndInput(WebElement input,Actions actions,String newName) {
                try {
                        /*WebElement deleteBtn = WebElementUtils.waitForElementWithRetry(By
                                .xpath("//*[name()='path' and contains(@d,'M512 64C26')]"), 5,driver);
                        //actions.moveToElement(deleteBtn).click(deleteBtn).perform();
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                        executor.executeScript("arguments[0].click();", deleteBtn);*/
                        input.sendKeys(newName);
                        Thread.sleep(500); // 添加人工延迟
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.error("删除操作中断", e);
                }
        }


        //删除功能
        public String deleteTable(List<WebElement> elements, Actions actions){
                try{
                        Random random=new Random();
                        WebElement table= elements.get(random.nextInt(elements.size()));
                        WebElement svg=table.findElement(By.cssSelector(".action-icon.anticon.anticon-more"));
                        actions.moveToElement(svg).perform();
                        //返回被删除文件的文件按名
                        String tableName=table.findElement(By.cssSelector(".name-txt-inner")).getText();
                        //选择重命名
                        List<WebElement> selectform = WebElementUtils
                                .waitForElementsWithRetry(By.cssSelector(".action-item"), 5, driver);
                        //选择删除按钮
                        WebElement deleteBtn=selectform.get(3);
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                        executor.executeScript("arguments[0].click();", deleteBtn);
                        //点击确定删除
                        WebElement confirm = WebElementUtils
                                .waitForElementWithRetry(By.xpath("//body//div//button[2]"), 7, driver);
                       //confirm.click();
                        executor.executeScript("arguments[0].click();", confirm);
                        return  tableName;

                }catch (Exception e){
                        logger.error("删除失败",e);
                }
                throw new RuntimeException("删除操作异常");

        }


        /**
         * 到我管理的页面，随机选择一个文件进行操作
         * 拿到表单元素，选择收藏操作，判断是否是收藏或则取消收藏
         * 如果是收藏，跳转到收藏页面查看操作是否成功
         * 如果是取消收藏，跳转到收藏页面，进行判断
         */
        //收藏功能,包括取消收藏
        public List<String> favoritesTable(List<WebElement> elements, Actions actions){
                Random random=new Random();
                try{
                        List<String> name=new ArrayList<>();
                        WebElement table= elements.get(random.nextInt(elements.size()));
                        WebElement svg=table.findElement(By.cssSelector(".action-icon.anticon.anticon-more"));
                        actions.moveToElement(svg).perform();
                        List<WebElement> selectform = WebElementUtils
                                .waitForElementsWithRetry(By.cssSelector(".action-item"), 5, driver);
                        //选择收藏/取消收藏
                        WebElement favirote = selectform.get(2);
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                        executor.executeScript("arguments[0].click();", favirote);
                        name.add(0,favirote.getText());
                        name.add(1, table.findElement(By.cssSelector(".name-txt-inner")).getText());
                        return name;
                }catch (Exception e){
                        logger.error("关于收藏操作失败",e);
                }
                throw new RuntimeException("收藏操作异常");


        }





        //搜索功能
        public void searchByTableName(String tableName){
              try{
                      //点击搜索按钮
                      searchButton.clear();
                      //输入文本
                      searchButton.sendKeys(tableName);
                      //回车
                      searchButton.sendKeys(Keys.ENTER);

              }catch (Exception e){
                      logger.error("搜索操作失败",e);
                      throw new RuntimeException("搜索操作异常");
              }


        }


        //还原功能
        public String recycleFile(List<WebElement> webElements, Actions actions){
               try {
                       Random random=new Random();
                       WebElement table= webElements.get(random.nextInt(webElements.size()));
                       WebElement svg=table.findElement(By.cssSelector(".action-icon.anticon.anticon-more"));
                       actions.moveToElement(svg).perform();
                       List<WebElement> selectform =WebElementUtils
                               .waitForElementsWithRetry(By.cssSelector(".action-item"), 5, driver);
                       //选择还原按钮
                       WebElement recycleBtn = selectform.get(0);
                       JavascriptExecutor executor = (JavascriptExecutor) driver;
                       executor.executeScript("arguments[0].click();", recycleBtn);
                       //返回被删除文件的文件按名
                       String tableName=table.findElement(By.cssSelector(".name-txt-inner")).getText();
                       //点击确定删除
                       WebElement confirm = WebElementUtils
                               .waitForElementWithRetry(By.xpath("//body//div//button[2]"), 5, driver);
                       //confirm.click();
                       executor.executeScript("arguments[0].click();", confirm);
                       return  tableName;
               }catch (Exception e){
                       logger.error("还原文件失败",e);
               }
              throw new RuntimeException("还原操作异常");

        }

        //彻底删除功能
        public String permanentlyDeleteFile(List<WebElement> webElements, Actions actions){
                Random random=new Random();
                try {
                       WebElement table= webElements.get(random.nextInt(webElements.size()));
                       WebElement svg=table.findElement(By.cssSelector(".action-icon.anticon.anticon-more"));
                       actions.moveToElement(svg).perform();
                       List<WebElement> selectform =WebElementUtils
                               .waitForElementsWithRetry(By.cssSelector(".action-item"), 5, driver);
                       //选择还原按钮
                       WebElement perDeleteBtn=selectform.get(1);
                       JavascriptExecutor executor = (JavascriptExecutor) driver;
                       executor.executeScript("arguments[0].click();", perDeleteBtn);
                       String tableName=table.findElement(By.cssSelector(".name-txt-inner")).getText();
                       //点击确定删除
                       WebElement confirm = WebElementUtils
                               .waitForElementWithRetry(By.xpath("//body//div//button[2]"), 5, driver);
                       //confirm.click();
                        executor.executeScript("arguments[0].click();", confirm);
                       return  tableName;

               }catch (Exception e){
                       logger.error("彻底删除失败",e);

               }
               throw new RuntimeException("彻底删除操作异常");


        }














}
