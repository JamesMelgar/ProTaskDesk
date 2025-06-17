package com.gyt.seguros.pro.task.desk;

import com.gyt.seguros.pro.task.desk.ui.LoginScreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class ProTaskDesk {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        // Asegurar que no está en modo headless
        System.setProperty("java.awt.headless", "false");

        applicationContext = SpringApplication.run(ProTaskDesk.class, args);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen();
            }
        });
    }

    public static <T> T getBean(Class<T> beanClass) {
        if (applicationContext != null) {
            return applicationContext.getBean(beanClass);
        }
        return null;
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}