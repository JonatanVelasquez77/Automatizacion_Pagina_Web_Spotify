package pages;

import org.openqa.selenium.Keys;
import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaginaBusqueda extends PaginaBase{
    private WebDriver driver = DriverFactory.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    private final String searchInput = "//input[contains(@data-testid,'search-input')]";
    private By primerResultado = By.xpath("//button[@aria-label='Reproducir Shape of You de Ed Sheeran']");

    public void search(String term) {
        write(By.id(searchInput), term);
        find(By.id(searchInput)).sendKeys(Keys.ENTER);
    }

    public void seleccionarPrimerResultado() {
        try {
            // Esperar y hacer click directamente en el primer resultado
            wait.until(ExpectedConditions.elementToBeClickable(primerResultado));
            click(primerResultado);
            Thread.sleep(5000); // Esperar 3 segundos para asegurar que la acción se complete
        } catch (Exception e) {
            System.out.println("Error con método simple: " + e.getMessage());
            // Fallback al método anterior
            seleccionarPrimerResultado();
        }
    }
}
