package com.gyt.seguros.pro.task.desk;
import com.gyt.seguros.pro.task.desk.ui.LoginScreen;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class ProTaskDesk {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen();
            }
        });
    }
}
