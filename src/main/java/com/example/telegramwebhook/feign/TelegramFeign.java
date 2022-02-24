package com.example.telegramwebhook.feign;

import com.example.telegramwebhook.dto.FileTelegram;
import com.example.telegramwebhook.dto.ResultTelegram;
import com.example.telegramwebhook.dto.SendPhotoOwn;
import com.example.telegramwebhook.constant.TgAction;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@FeignClient(url = "${telegram.api-url}",name = "TelegramFeign")
public interface TelegramFeign {

    @PostMapping("{path}"+ TgAction.SEND_MESSAGE)
    ResultTelegram<Message> sendMessageToUser(
            @PathVariable("path") String path,
            @RequestBody SendMessage sendMessage);

    @PostMapping("{path}"+TgAction.SEND_PHOTO)
    ResultTelegram<Message> sendPhotoToUser(
            @PathVariable("path") String path,
            @RequestBody SendPhotoOwn sendPhoto);

    @RequestMapping("{path}"+ TgAction.SET_WEBHOOK)
    void init(
            @PathVariable("path") String path);

    @RequestMapping("{path}"+ TgAction.SET_WEBHOOK)
    void setWebhook(
            @PathVariable("path") String path,
            @RequestParam("url") String webhookPath);

    @RequestMapping(value = "{path}" + TgAction.SEND_DOCUMENT, method = RequestMethod.POST)
    @Headers("Content-Type: multipart/form-data")
    void sendDocument(
            @PathVariable("path")  String path,
            @RequestParam("chat_id") String chat_id,
            @RequestParam("document") String document,
            @RequestParam(value = "caption",required = false) String caption,
            @RequestParam(value = "parse_mode",required = false) String parse_mode
    );

    @RequestMapping(value = "{path}"+ TgAction.GET_FILE,method = RequestMethod.GET)
    ResultTelegram<FileTelegram> getFile(
            @PathVariable("path") String path,
            @RequestParam("file_id") String file_id);
}
