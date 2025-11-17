package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.HashMap;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // CONFIGURACIÓN DE IDIOMA - FORZAR ESPAÑOL
            HashMap<String, Object> prefs = new HashMap<>();
            prefs.put("intl.accept_languages", "es,es-ES,es-MX,es-AR");

            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--lang=es");
            options.addArguments("--accept-lang=es-ES");

            // Detectar entorno CI
            boolean isCI = "CI".equals(System.getenv("RUN_ENV"));

            if (isCI) {
                // CONFIGURACIÓN OPTIMIZADA PARA GITHUB ACTIONS
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-extensions");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-features=VizDisplayCompositor");
                options.addArguments("--disable-software-rasterizer");

                // User agent realista
                options.addArguments(
                        "user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36;lang=es"
                );

                System.out.println("🔧 Ejecutando en modo CI (GitHub Actions)");
            } else {
                // CONFIGURACIÓN LOCAL
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-notifications");
                System.out.println(" Ejecutando en modo LOCAL");
            }

            WebDriver driver = new ChromeDriver(options);

            // TIMEOS MÁS LARGOS PARA CI
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
            } catch (Exception e) {
                System.out.println(" Error al cerrar driver: " + e.getMessage());
            }
            DRIVER.remove();
        }
    }
}