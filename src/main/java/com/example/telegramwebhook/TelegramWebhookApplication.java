package com.example.telegramwebhook;

import com.example.telegramwebhook.constant.TgConstant;
import com.example.telegramwebhook.feign.TelegramFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class TelegramWebhookApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TelegramWebhookApplication.class, args);
        TelegramFeign feign = run.getBean(TelegramFeign.class);
        feign.init("bot"+TgConstant.TOKEN);
        feign.setWebhook("bot"+TgConstant.TOKEN,TgConstant.ME_SERVER);
    }

}
