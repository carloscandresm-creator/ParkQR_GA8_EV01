
package com.parkqr.util;

import com.parkqr.dao.CobroDAO;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class TareaCobrosAutomaticos implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {

        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {

            try {
                CobroDAO cobroDAO = new CobroDAO();

                cobroDAO.actualizarCobrosAutomaticos();

            } catch (Exception e) {
                System.out.println(
                        "Error en la tarea automática de cobros: "
                                + e.getMessage()
                );
                e.printStackTrace();
            }

        }, 0, 1, TimeUnit.MINUTES);

        System.out.println("Tarea automática de cobros iniciada.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        if (scheduler != null) {
            scheduler.shutdownNow();
        }

        System.out.println("Tarea automática de cobros detenida.");
    }
}
