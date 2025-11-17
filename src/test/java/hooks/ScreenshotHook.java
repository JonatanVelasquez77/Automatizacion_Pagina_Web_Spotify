package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import driver.DriverFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Base64;

public class ScreenshotHook {

    @After
    public void takeScreenshotForFailedScenario(Scenario scenario) throws IOException {

        if (!scenario.isFailed()) {
            return; // Solo si falla
        }


        // Tomar Screenshot Selenium

        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);

        String base64 = Base64.getEncoder().encodeToString(screenshotBytes);


        // Adjuntar screenshot en ALLURE

        Allure.addAttachment(
                "Screenshot - Error",
                "image/png",
                new ByteArrayInputStream(screenshotBytes),
                "png"
        );


        // Guardar screenshot para JIRA FAKE

        File bugDir = new File("build/bugs");
        bugDir.mkdirs();

        String timestamp = LocalDateTime.now().toString().replace(":", "-");
        String screenshotFileName = "BUG-" + timestamp + ".png";

        File screenshotFile = new File(bugDir, screenshotFileName);

        try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
            fos.write(screenshotBytes);
        }


        // Crear archivo .md con link al screenshot

        String bugTextFile = "build/bugs/BUG-" + timestamp + ".md";
        FileWriter writer = new FileWriter(bugTextFile);

        writer.write("""
                # Simulación de Ticket JIRA
                **Estado:** OPEN
                **Prioridad:** Alta  
                **Escenario:** %s  
                **Fecha:** %s  

                ## Descripción
                El escenario falló durante la ejecución automática en GitHub Actions.

                ## Evidencia (Screenshot)
                Archivo: %s

                ## Base64 para portales JIRA
                ```
                %s
                ```
                """.formatted(
                scenario.getName(),
                timestamp,
                screenshotFileName,
                base64
        ));

        writer.close();

        System.out.println("JIRA Fake ticket generado: " + bugTextFile);
    }
}

