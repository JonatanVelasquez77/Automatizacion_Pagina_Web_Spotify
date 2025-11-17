package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {

        if (DRIVER.get() == null) {

            boolean isCI = "CI".equals(System.getenv("RUN_ENV"));

            ChromeOptions options = new ChromeOptions();

            if (isCI) {
                System.out.println("🔧 Ejecutando en CI (GitHub Actions)");

                // Chrome binary
                String chromeBinary = System.getenv("CHROME_BINARY");
                if (chromeBinary != null) {
                    options.setBinary(chromeBinary);
                }

                // Driver path
                String chromeDriverPath = System.getenv("CHROMEDRIVER_PATH");
                if (chromeDriverPath != null) {
                    System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                }

                // Headless
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-software-rasterizer");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setExperimentalOption("useAutomationExtension", false);

            } else {
                System.out.println("🖥 Ejecutando en modo LOCAL");

                // Aquí sí usas WebDriverManager
                WebDriverManager.chromedriver().setup();

                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
            }

            WebDriver driver = new ChromeDriver(options);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            DRIVER.set(driver);
        }

        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
