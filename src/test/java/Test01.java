import com.get.automation.config.MyProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

public class Test01 {
    public static void main(String[] args) {

        //自动处理 GeckoDriver 的下载和设置
        //WebDriverManager.firefoxdriver().setup();

        // 设置 GeckoDriver 的路径（绝对路径或相对路径）
        System.setProperty("webdriver.chrome.driver",
                "C:\\dev2\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // 创建一个新的 Chrome 浏览器实例
        WebDriver driver = new ChromeDriver();
        //WebElement element = driver.findElement(By.cssSelector());

        // 创建一个表示10秒的持续时间
        Duration thirtySeconds = Duration.ofSeconds(30);
        try {
            // 打开钉钉网页版登录页面
            driver.get("https://login.dingtalk.com/");

            // 等待页面加载完成，并找到用户名和密码输入框,显示等待
            WebDriverWait wait = new WebDriverWait(driver, thirtySeconds);
            WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mobile")));
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pwd")));

            // 输入用户名和密码
            usernameField.sendKeys("17361379708");
            passwordField.sendKeys("xianlei200188");

            // 找到登录按钮并点击
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".login-btn")));
            loginButton.click();

            // 这里可以添加更多逻辑来验证是否成功登录，例如检查特定元素是否存在
            Properties properties= MyProperties.getProperties();
            int size = properties.size();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭浏览器
            // 注意：在实际测试中，你可能不希望立即关闭浏览器，以便观察结果
            driver.quit();
        }
    }
}
