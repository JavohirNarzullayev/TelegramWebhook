package com.example.telegramwebhook.controller;

import com.example.telegramwebhook.constant.SendPhotoOwn;
import com.example.telegramwebhook.constant.TgConstant;
import com.example.telegramwebhook.feign.TelegramFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.File;

@RestController("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TelegramFeign telegramFeign;

    @GetMapping("/sendPhoto")
    public void sendPhoto(){
        SendPhotoOwn sendPhotoOwn=new SendPhotoOwn(
                1982037028+"",
                "Asosiy mavzu",
                "https://storage.kun.uz/source/thumbnails/_medium/7/YK8plH3V0vLOWv4F5sw3sdW82AmZUpAu_medium.jpg"
        );

        telegramFeign.sendPhotoToUser("bot"+ TgConstant.TG_BASE_URL_WITHOUT_BOT,sendPhotoOwn);

    }
}
