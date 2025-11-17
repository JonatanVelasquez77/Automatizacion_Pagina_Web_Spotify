package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaginaPlaylist extends PaginaBase {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    private By btnMusica = By.xpath("//*[normalize-space(text()) = \"Música\"][1]");
    private By btnPlaylistGlobal = By.xpath("//*[@id=\"card-title-spotify:playlist:37i9dQZEVXbMDoHDwVN2tF-2\"]");
    private By txtPlaylistGlobal = By.xpath("//h1[contains(text(),'Top 50: Global')]");

    public void irAMusica() {
        wait.until(ExpectedConditions.elementToBeClickable(btnMusica));
        click(btnMusica);
    }

    public boolean existePlaylistGlobal() {
        try {
            // 1. Hacer click en la playlist
            wait.until(ExpectedConditions.elementToBeClickable(btnPlaylistGlobal));
            click(btnPlaylistGlobal);

            // 2. Esperar a que cargue la nueva página y verificar elemento de confirmación
            wait.until(ExpectedConditions.visibilityOfElementLocated(txtPlaylistGlobal));
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}

