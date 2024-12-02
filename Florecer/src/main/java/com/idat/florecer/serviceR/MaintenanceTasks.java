package com.idat.florecer.serviceR;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceTasks {

	@Scheduled(cron = "0 0 0 * * ?") 
    public void performDailyBackup() {
        executeBackup();
    }

    private void executeBackup() {
        try {
            System.out.println("Iniciando el proceso de respaldo...");

            // Comando para ejecutar mysqldump en Windows
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/C://backups_%date:~10",
                "mysqldump -u root -p1234 bd_florecer > C:\backups_%date:~10,4%-%date:~7,2%-%date:~4,2%.sql");

            // Configura el directorio donde se ejecutará el comando
            processBuilder.directory(new File("C://backups"));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Backup realizado con éxito.");
            } else {
                System.err.println("Error al realizar el backup. Código de salida: " + exitCode);
            }
        } catch (Exception e) {
            System.err.println("Excepción durante el respaldo: " + e.getMessage());
        }
    }
}
