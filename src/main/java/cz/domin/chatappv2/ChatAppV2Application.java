package cz.domin.chatappv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatAppV2Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ChatAppV2Application.class);
        application.setAddCommandLineProperties(false);
        application.run(args);
    }
}
