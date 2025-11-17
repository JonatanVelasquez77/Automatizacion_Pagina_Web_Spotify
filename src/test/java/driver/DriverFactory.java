package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {

        if (DRIVER.get() == null) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // Detectar entorno
            String env = System.getenv("RUN_ENV");
            boolean isCI = env != null && env.equalsIgnoreCase("CI");

            if (isCI) {
                //  CONFIGURACIÓN PARA GITHUB ACTIONS
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-extensions");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments(
                        "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/120.0.0.0 Safari/537.36"
                );

                System.out.println(" Ejecutando en modo CI (GitHub Actions)");

            } else {
                // CONFIGURACIÓN LOCAL
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-notifications");

                System.out.println("Ejecutando en modo LOCAL");
            }

            WebDriver driver = new ChromeDriver(options);
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
