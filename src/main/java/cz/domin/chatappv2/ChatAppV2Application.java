package cz.domin.chatappv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class ChatAppV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ChatAppV2Application.class, args);
    }

}
