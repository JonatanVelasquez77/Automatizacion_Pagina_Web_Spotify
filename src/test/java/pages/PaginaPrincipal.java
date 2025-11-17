package pages;

import org.openqa.selenium.By;

public class PaginaPrincipal extends PaginaBase {

    private final By searchNav = By.xpath("//input[contains(@data-testid,'search-input')]");
    private final By exploreNav = By.xpath("//*[@data-testid=\"browse-button\"]");

    public void openPage() {
        openUrl("https://open.spotify.com/");
    }

    public void goToSearch() {
        try {
            click(searchNav);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo navegar a la pagina de busqueda: " + e.getMessage());
        }
    }

    public void buscar(String texto) {
        write(searchNav, texto);
    }

    public void irAExplorar() {
        try {
            click(exploreNav);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo navegar a la pagina de busqueda: " + e.getMessage());
        }
    }
}

