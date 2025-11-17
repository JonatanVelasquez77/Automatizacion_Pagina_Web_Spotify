package hooks;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setUp() {
        if ("CI".equals(System.getenv("RUN_ENV"))) {
            DriverFactory.getDriver().manage().deleteAllCookies();
        }
        Allure.step("Inicializando WebDriver");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Tomar screenshot en caso de fallo
            try {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
                System.out.println("Screenshot tomado para scenario fallido: " + scenario.getName());
            } catch (Exception e) {
                System.out.println("Error tomando screenshot: " + e.getMessage());
            }
        }

        // NO cerrar el driver después de cada scenario para mejor performance
        // Solo limpiar cookies
        if ("CI".equals(System.getenv("RUN_ENV"))) {
            DriverFactory.getDriver().manage().deleteAllCookies();
        }
    }

    // Hook para cerrar el driver al final de todo
    @AfterAll
    public static void closeDriver() {
        DriverFactory.quitDriver();
    }
}
