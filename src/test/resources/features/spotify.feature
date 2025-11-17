Feature:Funcionalidad principal de Spotify Web

    @Busqueda
    Scenario: Busqueda de canciones/artistas
        Given Estoy en la pagina principal de Spotify Web
        When busco el termino "Coldplay"
        Then deberia visualizar resultados relacionados con "Coldplay"

    @Login
    Scenario: Login con credenciales inválidas
        Given que estoy en la página de login
        When ingreso un correo y contraseña "clave_invalida1"
        Then debería mostrarse un mensaje de error en el login

    @Playlists
    Scenario: Navegación a playlists populares
        Given que estoy logueado correctamente
        When navego a Explorar y selecciono Musica
        Then debería visualizar una playlist pública llamada Top 50: Global

    @Reproduccion
    Scenario: Reproducir una canción
        Given que estoy en la página principal de Spotify
        When busco la canción "Shape of You"
        And selecciono el primer resultado
        Then la canción debería comenzar a reproducirse

    @AgregarMeGusta
    Scenario: Agregar canción a Tus Me Gusta
        Given que estoy en la página principal de Spotify
        When busco la canción "Believer"
        And la agrego a mis canciones favoritas
        Then la canción "Believer" debería aparecer en la lista Tus Me Gusta