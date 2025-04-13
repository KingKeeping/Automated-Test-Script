package com.get.automation.pojo;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 页面对象封装
public class FileHomePage {
    private WebDriver driver;
    
    // 元素定位器
    @FindBy(css = ".quick-create")
    WebElement createBtn;
    @FindBy(css = ".search-input")
    WebElement searchInput;
    @FindBy(css = ".search-type")
    WebElement searchType;
    @FindBy(css = ".file-list")
    WebElement fileList;

    public FileHomePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void quickCreateFile(String fileName) {
        createBtn.click();
        new MultiDimensionalFilePage(driver).renameFile(fileName);
    }

    public void searchFiles(String type, String keyword) {
        selectSearchType(type);
        //输入文件名并回车
        searchInput.sendKeys(keyword + Keys.ENTER);

    }

    public void navigateTo(String listName) {
        driver.findElement(By.xpath("//button[text()='" + listName + "']"))
                .click();

    }

    public void operateFile(String fileName, String action) {
        WebElement fileItem = findFileItem(fileName);
        fileItem.findElement(By.xpath(".//button[contains(text(),'" + action + "')]")).click();
        handleConfirmation();

    }

    public boolean isPaginationVisible() {
        return !driver.findElements(By.cssSelector(".pagination")).isEmpty();
    }

    public int getPageCount() {
        return driver.findElements(By.cssSelector(".page-item:not(.prev):not(.next)")).size();
    }

    public Map<String, Integer> getTimeGroups() {
        return driver.findElements(By.cssSelector(".time-group"))
                   .stream()
                   .collect(Collectors.toMap(
                       e -> e.findElement(By.cssSelector(".group-title")).getText(),
                       e -> e.findElements(By.cssSelector(".file-item")).size()
                   ));
    }

    public boolean isTimeSortedDescending() {
        List<LocalDateTime> dates = driver.findElements(By.cssSelector(".file-item"))
            .stream()
            .map(e -> LocalDateTime.parse(e.getAttribute("data-time"), 
                 DateTimeFormatter.ISO_DATE_TIME))
            .collect(Collectors.toList());
        
        for (int i = 0; i < dates.size()-1; i++) {
            if (dates.get(i).isBefore(dates.get(i+1))) {
                return false;
            }
        }
        return true;
    }

    private WebElement findFileItem(String fileName) {
        return driver.findElement(By.xpath(
            "//div[contains(@class,'file-item')][.//span[text()='" + fileName + "']]"));
    }

    private void selectSearchType(String type) {
        // 实现搜索类型选择逻辑
    }

    private void handleConfirmation() {
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) {}
    }


}
