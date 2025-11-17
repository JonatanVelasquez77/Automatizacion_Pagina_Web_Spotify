package hooks;

import driver.DriverFactory;
import io.cucumber.java.After;
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
        driver = DriverFactory.getDriver();
        Allure.step("Inicializando WebDriver");
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on Failure", "image/png",
                        new java.io.ByteArrayInputStream(screenshot),
                        ".png");
            }
            Allure.step("Finalizando test y tomando captura...");

            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment("Screenshot final", "image/png",
                    new java.io.ByteArrayInputStream(screenshot), "png");

        } catch (Exception e) {
            Allure.step("No se pudo tomar screenshot final");
        }

        DriverFactory.quitDriver();
    }
}
