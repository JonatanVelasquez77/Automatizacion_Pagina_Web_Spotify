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

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            boolean isCI = "CI".equals(System.getenv("RUN_ENV"));

            if (isCI) {
                System.out.println("🔧 Ejecutando en CI (GitHub Actions)");

                // Headless pero con ventana simulada
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");

                // Evitar detección de automatización
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setExperimentalOption("useAutomationExtension", false);

                // Optimización Linux
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-software-rasterizer");

                // Evitar errores gráficos
                options.addArguments("--disable-features=VizDisplayCompositor");

                // Lenguaje realista
                options.addArguments("--lang=es-ES");

                // User agent realista (Chrome 142, que es el que usa GitHub Actions)
                options.addArguments(
                        "user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
                                + "(KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36"
                );
                // Forzar usar el Chrome real del runner
                String chromeBinary = System.getenv("CHROME_BINARY");
                if (chromeBinary != null && !chromeBinary.isEmpty()) {
                    System.out.println("Usando chrome binary: " + chromeBinary);
                    options.setBinary(chromeBinary);
                }

            } else {
                System.out.println(" Ejecutando en modo LOCAL");

                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--remote-allow-origins=*");
            }

            WebDriver driver = new ChromeDriver(options);

            if (isCI) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
                driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
            } else {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            }

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
