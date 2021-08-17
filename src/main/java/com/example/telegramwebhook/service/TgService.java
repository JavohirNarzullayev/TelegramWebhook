package com.example.telegramwebhook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TgService {
    private final WebhookService webHookService;

    public void updateWait(Update update){
        if (update.hasMessage()) {
            String message = update.getMessage().getText();
            switch (message){
                case "/start":{
                    webHookService.whenStart(update);
                }

            }
        }else if (update.hasCallbackQuery()){
            System.out.println("CallBack-"+update.getCallbackQuery().getMessage());
        }


    }
}
