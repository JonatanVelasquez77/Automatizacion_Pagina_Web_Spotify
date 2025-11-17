package pages;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PaginaFavoritos extends PaginaBase{

    private WebDriver driver = DriverFactory.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    private By corazon = By.xpath("(//button[@aria-label='Añadir a Canciones que te gustan'])[1]");
    private By playlistFavoritos = By.xpath("//span[text()='Tus me gusta']");

    public void agregarAFavoritos() {
        try {
            // Esperar a que el botón esté disponible
            wait.until(ExpectedConditions.elementToBeClickable(corazon));
            click(corazon);

            // Esperar a que se confirme la acción
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error al agregar a favoritos: " + e.getMessage());
            throw new RuntimeException("No se pudo agregar a favoritos");
        }
    }

    public boolean verificarCancionEnLista(String nombreCancion) {
        try {
            // Ir a la lista de "Tus me gusta"
            wait.until(ExpectedConditions.elementToBeClickable(playlistFavoritos));
            click(playlistFavoritos);

            // Espera 3 seg
            Thread.sleep(3000);

            // Buscar la canción con diferentes patrones
            List<WebElement> canciones = driver.findElements(
                    By.xpath("//div[contains(@class, 'track-name')]//span[contains(text(), '" + nombreCancion + "')] | " +
                            "//a[contains(@data-testid, 'track')]//div[contains(text(), '" + nombreCancion + "')] | " +
                            "//div[contains(text(), '" + nombreCancion + "')]")
            );

            System.out.println("Canciones encontradas con '" + nombreCancion + "': " + canciones.size());

            // Verificar que al menos una esté visible
            for (WebElement cancion : canciones) {
                if (cancion.isDisplayed()) {
                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            System.out.println("Error verificando canción en lista: " + e.getMessage());
            return false;
        }
    }
}

