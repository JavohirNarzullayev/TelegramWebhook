package com.example.telegramwebhook.service;

import com.example.telegramwebhook.handler.CallBackQueryHandler;
import com.example.telegramwebhook.handler.MessageHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public record TgService(WebhookService webHookService,
                        CallBackQueryHandler callbackQueryHandler,
                        MessageHandler messageHandler) {

    public void onWebhookUpdateReceived(Update update) throws Exception {
        handleUpdate(update);
    }

    private void handleUpdate(Update update) throws Exception {
        if (update.hasCallbackQuery()) {
            callbackQueryHandler.processCallbackQuery(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            messageHandler.answerMessage(update.getMessage());
        }
    }
}
