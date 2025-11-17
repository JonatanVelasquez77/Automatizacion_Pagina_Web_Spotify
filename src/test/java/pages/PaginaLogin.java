package pages;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaginaLogin extends PaginaBase {

    private WebDriver driver = DriverFactory.getDriver();

    private By campoEmail = By.id("username");
    private By campoPassword = By.id("password");
    private By btnLogin = By.xpath("//button[contains(text(),'Continuar') or contains(text(),'Continue') or @type='submit']");
    private By mensajeError = By.xpath("//span[contains(text(),'Nombre de usuario o contraseña incorrectos.')]");
    private By btnIngContrasena = By.xpath("//button[@type='button']");
    private By btnReproductorWeb = By.xpath("//*[contains(@data-testid,'web-player-link')]");

    public void abrirPagina() {
        driver.get("https://accounts.spotify.com/es/v2/login");
    }

    public void ingresarCorreo(String correo) {
        write(campoEmail, correo);
    }

    public void ingresarClave(String clave) {
        write(campoPassword, clave);
    }

    public void hacerLogin() {
        click(btnLogin);
    }

    public String obtenerMensajeError() {
        return find(mensajeError).getText();
    }

    public void ingresarSoloContrasena() {
        click(btnIngContrasena);
    }

    public void abrirReproductorWeb() {
        click(btnReproductorWeb);
    }
}

