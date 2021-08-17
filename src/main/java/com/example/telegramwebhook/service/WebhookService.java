package com.example.telegramwebhook.service;

import com.example.telegramwebhook.constant.ResultTelegram;
import com.example.telegramwebhook.constant.SendPhotoOwn;
import com.example.telegramwebhook.constant.TgConstant;
import com.example.telegramwebhook.feign.TelegramFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final TelegramFeign telegramFeign;



    public void whenStart(Update update) {
        SendMessage sendMessage=new SendMessage(update.getMessage().getChatId(),"Hello telegram");
        ResultTelegram resultTelegram = telegramFeign.sendMessageToUser("bot" + TgConstant.TOKEN, sendMessage);
        System.out.println(resultTelegram);
        SendPhotoOwn sendPhotoOwn=new SendPhotoOwn(
                update.getMessage().getChatId().toString(),
                "Asosiy mavzu",
                "https://storage.kun.uz/source/thumbnails/_medium/7/YK8plH3V0vLOWv4F5sw3sdW82AmZUpAu_medium.jpg"
        );
        String fileId=null;
        for (int i = 0; i < 14; i++) {
            if (fileId!=null){
                sendPhotoOwn.setPhoto(fileId);
            }
            ResultTelegram resultTelegram1 = telegramFeign.sendPhotoToUser("bot" + TgConstant.TOKEN, sendPhotoOwn);
            if (fileId==null){
                List<PhotoSize> photos = resultTelegram1.getResult().getPhoto();
                PhotoSize photoSize = photos.get(photos.size() - 1);
                fileId=photoSize.getFileId();
            }
        }

    }

    public void sendPhoto(){}
}
