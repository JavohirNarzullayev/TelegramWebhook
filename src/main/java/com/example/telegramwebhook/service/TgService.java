package com.example.telegramwebhook.service;

import com.example.telegramwebhook.handler.CallBackQueryHandler;
import com.example.telegramwebhook.handler.MessageHandler;
import com.example.telegramwebhook.keyboard.BotMessageEnum;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;

@Service
public record TgService(WebhookService webHookService,
                        CallBackQueryHandler callbackQueryHandler,
                        MessageHandler messageHandler) {


    public void onWebhookUpdateReceived(Update update) {
        try {
            handleUpdate(update);
        } catch (Exception exception) {
            switch (exception) {
                case TelegramApiRequestException ignored -> exceptionHandler(update, BotMessageEnum.EXCEPTION_API);
                case IllegalArgumentException ignored ->
                        exceptionHandler(update, BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE);
                default -> exceptionHandler(update, BotMessageEnum.EXCEPTION_WHAT_THE_FUCK);
            }
            exception.printStackTrace();
        }
    }

    private void exceptionHandler(Update update, BotMessageEnum exceptionApi) {
        var sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                exceptionApi.getMessage());
        webHookService.sendMessageToUser(sendMessage);
    }

    private void handleUpdate(Update update) throws TelegramApiRequestException, IOException {
        if (update.hasCallbackQuery()) {
            callbackQueryHandler.processCallbackQuery(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            messageHandler.answerMessage(update.getMessage());
        }
    }
}
