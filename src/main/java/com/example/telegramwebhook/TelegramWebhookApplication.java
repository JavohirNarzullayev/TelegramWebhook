package com.example.telegramwebhook;

import com.example.telegramwebhook.service.WebhookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TelegramWebhookApplication {

    public static void main(String[] args) {
        var run = SpringApplication.run(TelegramWebhookApplication.class, args);
        run.getBean(WebhookService.class).init();
    }

}
