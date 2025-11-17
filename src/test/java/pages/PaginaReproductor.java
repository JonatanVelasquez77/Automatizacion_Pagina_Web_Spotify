package pages;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaginaReproductor extends PaginaBase{

    private static WebDriver driver = DriverFactory.getDriver();


    // El botón de Play/Pause cambia dinámicamente su aria-label
    private static By playPauseButton = By.xpath("//button[@data-testid='control-button-playpause']");

    public static boolean estaReproduciendo() {
        try {
            // Esperar a que los controles estén disponibles
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(playPauseButton));

            // Método 1: Buscar directamente el botón de Pause
            if (driver.findElements(playPauseButton).size() > 0) {
                System.out.println("DEBUG: Se encontró botón de Pause - ESTÁ REPRODUCIENDO");
                return true;
            }

            // Método 2: Verificar el atributo aria-label del botón play/pause
            WebElement button = driver.findElement(playPauseButton);
            String estado = button.getAttribute("aria-label");
            System.out.println("DEBUG: Estado del botón: " + estado);

            if (estado != null) {
                // Dependiendo del idioma de la interfaz
                boolean reproduciendo = estado.equalsIgnoreCase("Pause") ||
                        estado.equalsIgnoreCase("Pausa") ||
                        estado.equalsIgnoreCase("Detener");
                System.out.println("DEBUG: Según aria-label, reproduciendo: " + reproduciendo);
                return reproduciendo;
            }

            return false;

        } catch (Exception e) {
            System.out.println("ERROR en estaReproduciendo(): " + e.getMessage());
            return false;
        }
    }
}

