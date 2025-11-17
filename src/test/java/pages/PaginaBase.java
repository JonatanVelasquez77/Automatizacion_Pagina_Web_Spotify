package pages;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PaginaBase {

    protected static WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait longWait;

    public PaginaBase() {
        this.driver = DriverFactory.getDriver();

        // Detectar si estamos en CI
        boolean isCI = "CI".equals(System.getenv("RUN_ENV"));

        if (isCI) {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            this.longWait = new WebDriverWait(driver, Duration.ofSeconds(45));
        } else {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            this.longWait = new WebDriverWait(driver, Duration.ofSeconds(25));
        }
    }

    protected void openUrl(String url) {
        driver.get(url);

        // Espera adicional en CI
        if ("CI".equals(System.getenv("RUN_ENV"))) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    protected WebElement find(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Elemento no encontrado: " + locator);
            throw e;
        }
    }

    protected WebElement findWithLongWait(By locator) {
        try {
            return longWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Elemento no encontrado con long wait: " + locator);
            throw e;
        }
    }

    protected List<WebElement> findAll(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    protected void click(By locator) {
        try {
            WebElement element = findWithLongWait(locator);
            element.click();

            // Pequeña pausa después del click en CI
            if ("CI".equals(System.getenv("RUN_ENV"))) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error al hacer click: " + e.getMessage());
            throw e;
        }
    }

    protected void write(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText();
    }

    public void selectFromDropdownByValue(By locator, String value) {
        Select dropdown = new Select(find(locator));
        dropdown.selectByValue(value);
    }

    public void selectFromDropdownByIndex(By locator, Integer index) {
        Select dropdown = new Select(find(locator));
        dropdown.selectByIndex(index);
    }

    public int dropdownSize(By locator) {
        Select dropdown = new Select(find(locator));
        List<WebElement> dropdownOptions = dropdown.getOptions();
        return dropdownOptions.size();
    }

    protected void switchToIframe(By iframeLocator) {
        WebElement iframe = (WebElement) wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
        driver.switchTo().frame(iframe);
    }



}
