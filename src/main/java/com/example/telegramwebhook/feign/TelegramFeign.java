package com.example.telegramwebhook.feign;

import com.example.telegramwebhook.constant.ResultTelegram;
import com.example.telegramwebhook.constant.SendPhotoOwn;
import com.example.telegramwebhook.constant.TgAction;
import com.example.telegramwebhook.constant.TgConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FeignClient(url = TgConstant.TG_BASE_URL_WITHOUT_BOT,name = "TelegramFeign")
public interface TelegramFeign {

    @PostMapping("{path}"+ TgAction.SEND_MESSAGE)
    ResultTelegram sendMessageToUser(
            @PathVariable("path") String path,
            @RequestBody SendMessage sendMessage);

    @PostMapping("{path}"+TgAction.SEND_PHOTO)
    ResultTelegram sendPhotoToUser(
            @PathVariable("path") String path,
            @RequestBody SendPhotoOwn sendPhoto);

    @RequestMapping("{path}"+ TgAction.SET_WEBHOOK)
    void init(
            @PathVariable("path") String token);

    @RequestMapping("{path}"+ TgAction.SET_WEBHOOK)
    void setWebhook(
            @PathVariable("path") String token,
            @RequestParam("url") String url);
}
