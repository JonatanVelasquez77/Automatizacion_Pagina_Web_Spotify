package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {

        if (DRIVER.get() == null) {

            // → Inyectar rutas desde GitHub Actions
            String chromeBinary = System.getenv("CHROME_BINARY");
            if (chromeBinary != null) {
                System.setProperty("webdriver.chrome.bin", chromeBinary);
                System.out.println("🔧 Chrome binary: " + chromeBinary);
            }

            String chromeDriverPath = System.getenv("CHROMEDRIVER_PATH");
            if (chromeDriverPath != null) {
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                System.out.println("🔧 ChromeDriver: " + chromeDriverPath);
            }

            ChromeOptions options = new ChromeOptions();

            boolean isCI = "CI".equals(System.getenv("RUN_ENV"));

            if (isCI) {
                System.out.println("🔧 Ejecutando en CI (GitHub Actions)");

                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");

                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-software-rasterizer");

                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setExperimentalOption("useAutomationExtension", false);

                // MUY IMPORTANTE PARA GITHUB
                options.addArguments("--remote-allow-origins=*");

                options.addArguments("--lang=es-ES");

            } else {
                System.out.println(" Ejecutando en modo LOCAL");

                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--remote-allow-origins=*");
            }

            WebDriver driver = new ChromeDriver(options);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(isCI ? 20 : 10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(isCI ? 40 : 30));

            DRIVER.set(driver);
        }

        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {}
            DRIVER.remove();
        }
    }
}
