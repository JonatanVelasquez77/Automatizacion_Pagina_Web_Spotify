# Automatización Web – Spotify

### Proyecto de automatización de pruebas funcionales para la WebApp de Spotify utilizando:

- Java 21

- Selenium WebDriver

- Cucumber (BDD)

- Gradle

- Allure Reports

- GitHub Actions CI/CD

- Screenshots automáticos

- Generación simulada de tickets JIRA al fallar una prueba

## 📌 1. Requisitos Previos

Antes de ejecutar el proyecto en local, asegúrarse de tener instalado:

- Java 21 (recomendado)
- Gradle
- Navegador Google Chrome
- Allure Commandline (opcional, para reportes)
- Git (opcional, para clonar el repositorio)

## 📁 2. Estructura del Proyecto

```
📦 Automatizacion_Pagina_Web_Spotify
┣ 📂 src
┃ ┣ 📂 main/java
┃ ┗ 📂 test/java
┃     ┣ 📂 steps        → Step Definitions (Cucumber)
┃     ┣ 📂 hooks        → Hooks (screenshots, setup/teardown)
┃     ┣ 📂 pages        → Page Objects (POM)
┃     ┣ 📂 driver       → DriverFactory (Selenium configs)
┃     ┗ 📂 utils        → Configuración y utilidades
┣ 📂 build/allure-results  → Resultados crudos de Allure
┣ 📂 allure-report         → Reporte HTML generado
┣ 📂 build/bugs            → Tickets JIRA simulados + screenshots
┣ 📂 .github/workflows     → Pipeline CI (GitHub Actions)
┣ 📄 README.md
┣ 📄 build.gradle
┣ 📄 config.properties.example
┗ 📄 gradlew / gradlew.bat
```

## 🔐 3. Configuración de Credenciales

El archivo original NO debe subirse:
```src/test/resources/config.properties```

#### En su lugar, se incluye:

```
spotify.email=usuario@ejemplo.com
spotify.password=MiPassword123
spotify.myEmail=
spotify.myPassword=
```

- Para local:

#### Crea config.properties copiando el .example.

- Para CI en GitHub:

#### Las credenciales se cargan desde GitHub Secrets:

| Variable             | Descripción                  |
| -------------------- | ---------------------------- |
| `SPOTIFY_EMAIL`      | Usuario falso de pruebas     |
| `SPOTIFY_PASSWORD`   | Password                     |
| `SPOTIFY_MYEMAIL`    | Credenciales reales/alternas |
| `SPOTIFY_MYPASSWORD` | Password                     |

Estas se inyectan al entorno CI mediante el workflow.

## ▶️ 4. Ejecutar las pruebas en LOCAL
1. Limpiar y ejecutar pruebas
```bash
./gradle clean test
``` 
2. Generar reporte Allure manualmente
```bash
allure generate build/allure-results --clean -o allure-report

```
3. Abrir reporte Allure en el navegador
```bash
allure serve build/allure-results
```
O tambien:
```
allure serve allure-results
```
4. Ver reportes en Cucumber Report (HTML Opcional Simple)
- Abrir el archivo generado:
```
build/reports/cucumber/Cucumber.html
```
## ▶️ 5. Ejecutar las pruebas en CI (GitHub Actions)

#### Las pruebas se ejecutan automáticamente al:

push

pull_request

#### Workflow principal:

📄 .github/workflows/allure-tests.yml

#### Incluye:

✔ Instalación Chrome

✔ Configuración Java

✔ Ejecución de pruebas

✔ Screenshots automáticos si falla

✔ Generación de Allure Report

✔ Publicación de artefactos

En Actions se podra descargar:

- allure-report → reporte HTML completo

- test-reports → reportes Cucumber/TestNG

- bug-reports → tickets JIRA simulados

- screenshots → capturas de errores

## 🧪 6. Comando para ejecutar un TAG específico
```bash
./gradlew clean test -Dcucumber.filter.tags="@mi_tag"
```

## 🖼️ 7. Screenshots y JIRA Fake Tickets

###### Si un escenario falla:

1. Se captura screenshot automáticamente

2. Se adjunta a Allure

3. Se genera ticket simulado JIRA:

📄 build/bugs/BUG-2025-01-19T14-22-10.md

📄 build/bugs/BUG-2025-01-19T14-22-10.png

## Formato del ticket:
```
# Simulación de Ticket JIRA
Estado: OPEN
Prioridad: Alta
Escenario: Login incorrecto

## Evidencia
BUG-2025-01-19T14-22-10.png
```

## 📊 8. Interpretando el Reporte Allure

### El reporte Allure incluye:

* Suites

### Muestra cada Feature de Cucumber.

* Historias y escenarios

### Detalles de cada Step ejecutado.

* Attachments

  - Screenshots

  - Logs

  - JSON de resultados

* Tendencias

### Ejecuciones pasadas vs actuales (si se guarda histórico).

*  ¿Cómo entender un escenario fallido?

###### En Allure se verá como:

🔴 Estado: Failed

📌 Step fallido

📷 Evidence → Screenshot - Error

📄 Error message

🧵 Stacktrace

###### Esto permite identificar rápidamente:

- Elementos no encontrados

- Cambios en UI

- Errores del sitio

- Problemas de tiempo de carga

## ⭐ 9. Prácticas implementadas

- Page Object Model (POM)
- Step Definitions con Cucumber
- Hooks para setup/teardown
- Configuración externa (config.properties)
- Screenshots automáticos
- Reportes Allure detallados
- Simulación de tickets JIRA
- CI/CD con GitHub Actions
- Ejecución por tags
- Manejo de esperas implícitas/explicitas


# Authors
- Jonatan Velasquez — QA Automation (2025)
