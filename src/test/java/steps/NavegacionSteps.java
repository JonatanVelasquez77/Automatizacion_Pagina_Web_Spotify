package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.*;
import utils.Config;

import static org.junit.Assert.assertTrue;

public class NavegacionSteps {
    // Instanciar paginas
    private PaginaPrincipal principal = new PaginaPrincipal();
    private PaginaResultados resultados = new PaginaResultados();
    private PaginaBusqueda busqueda = new PaginaBusqueda();
    private PaginaLogin login = new PaginaLogin();
    private PaginaFavoritos favoritos = new PaginaFavoritos();
    private PaginaPlaylist playlist = new PaginaPlaylist();

    // BUSQUEDA DE ARTISTAS
    @Given("Estoy en la pagina principal de Spotify Web")
    public void abrirSpotify() {
        principal.openPage();
        principal.goToSearch();
    }

    @When("busco el termino {string}")
    public void buscarElTermino(String termino) {
        busqueda.search(termino);
    }

    @Then("deberia visualizar resultados relacionados con {string}")
    public void validarResultados(String termino) {
        boolean encontrado = PaginaResultados.isArtistPresent(termino);
        assertTrue("El artista '" + termino + "' no aparece en los resultados", encontrado);
    }

    // LOGIN INVALIDO
    @Given("que estoy en la página de login")
    public void enLogin() {
        login.abrirPagina();
    }

    @When("ingreso un correo y contraseña {string}")
    public void ingresarCredsInvalidas(String clave) {
        login.ingresarCorreo(Config.getEmail());
        login.hacerLogin();
        login.ingresarSoloContrasena();
        login.ingresarClave(clave);
        login.hacerLogin();
    }

    @Then("debería mostrarse un mensaje de error en el login")
    public void validarError() {
        assertTrue(login.obtenerMensajeError().length() > 0);
    }


    // PLAYLISTS POPULARES
    @Given("que estoy logueado correctamente")
    public void loginCorrecto() {
        login.abrirPagina();
        login.ingresarCorreo(Config.getmyEmail());
        login.hacerLogin();
        login.ingresarSoloContrasena();
        login.ingresarClave(Config.getmyPassword());
        login.hacerLogin();
        login.abrirReproductorWeb();
    }

    @When("navego a Explorar y selecciono Musica")
    public void navegarA() {
        principal.irAExplorar();
        playlist.irAMusica();
    }

    @Then("debería visualizar una playlist pública llamada Top 50: Global")
    public void validarPlaylist() {
        boolean existe = playlist.existePlaylistGlobal();
        assertTrue("La playlist 'Top 50: Global' no se encuentra en la página", existe);
    }


    // REPRODUCIR CANCION
    @Given("que estoy en la página principal de Spotify")
    public void paginaPrincipal() {
//        login.abrirPagina();
//        login.ingresarCorreo(Config.getmyEmail());
//        login.hacerLogin();
//        login.ingresarSoloContrasena();
//        login.ingresarClave(Config.getmyPassword());
//        login.hacerLogin();
//        login.abrirReproductorWeb();
        principal.openPage();
    }

    @When("busco la canción {string}")
    public void buscar(String cancion) {
        principal.buscar(cancion);
    }

    @And("selecciono el primer resultado")
    public void seleccionarResultado() {
        busqueda.seleccionarPrimerResultado();
    }

    @Then("la canción debería comenzar a reproducirse")
    public void validarReproduccion() {
        assertTrue("La canción NO está reproduciéndose", PaginaReproductor.estaReproduciendo());
    }


    // FAVORITOS
    @When("la agrego a mis canciones favoritas")
    public void agregarFavoritos() {
        favoritos.agregarAFavoritos();
    }

    @Then("la canción {string} debería aparecer en la lista Tus Me Gusta")
    public void verificarEnFavoritos(String nombreCancion) {
        assertTrue(favoritos.verificarCancionEnLista(nombreCancion));
    }
}
