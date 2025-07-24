package com.gyt.seguros.pro.task.desk;

import com.gyt.seguros.pro.task.desk.ui.screen.LoginScreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class ProTaskDesk {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        applicationContext = SpringApplication.run(ProTaskDesk.class, args);

        SwingUtilities.invokeLater(LoginScreen::new);
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