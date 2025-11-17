package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class JiraReporter {

    @After
    public void reportBug(Scenario scenario) {
        if (!scenario.isFailed()) return;

        String title = scenario.getName();
        String timestamp = LocalDateTime.now().toString().replace(":", "-");
        String fileName = "build/bugs/Bug-" + timestamp + ".md";

        String content = """
                # Simulación Ticket JIRA
                **Estado:** OPEN  
                **Severidad:** Alta  
                **Escenario:** %s  
                **Fecha:** %s  

                ## Descripción
                El escenario falló durante la ejecución automática en GitHub Actions.

                ## Logs
                Revisar reporte Allure para más detalles.

                ## Evidencia
                (Aquí puedes insertar la imagen adjunta)
                """.formatted(title, timestamp);

        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(content);
            fw.close();
            System.out.println("Bug reportado: " + fileName);
        } catch (IOException e) {
            System.out.println("Error generando bug report: " + e.getMessage());
        }
    }
}

