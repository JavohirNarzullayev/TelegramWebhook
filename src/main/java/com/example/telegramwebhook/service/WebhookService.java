package com.example.telegramwebhook.service;

import com.example.telegramwebhook.constant.TelegramConstant;
import com.example.telegramwebhook.dto.FileTelegram;
import com.example.telegramwebhook.dto.ResultTelegram;
import com.example.telegramwebhook.dto.SendPhotoOwn;
import com.example.telegramwebhook.exceptions.TelegramException;
import com.example.telegramwebhook.feign.TelegramFeign;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

@Service
public record WebhookService(TelegramFeign telegramFeign, TelegramConstant telegramConstant) {

    public void sendDocument(String chat_id, String document, String caption, String parse_mode) {
        telegramFeign.sendDocument(
                telegramConstant.getPath(),
                chat_id,
                document,
                caption,
                parse_mode);
    }

    public ResultTelegram<Message> sendMessageToUser(SendMessage sendMessage) {
        try {
            return telegramFeign.sendMessageToUser(telegramConstant.getPath(), sendMessage);
        } catch (FeignException e) {
            throw new TelegramException.DefaultException(sendMessage.getChatId(), e.getMessage());
        }
    }

    public ResultTelegram<FileTelegram> getFile(String file_id) throws IOException {
        return telegramFeign.getFile(telegramConstant.getPath(), file_id);
    }

    public ResultTelegram<Message> sendPhotoToUser(SendPhotoOwn sendPhotoOwn) {
        return telegramFeign.sendPhotoToUser(telegramConstant.getPath(), sendPhotoOwn);
    }

    public void init() {
        try {
            telegramFeign.init(telegramConstant.getPath());
            telegramFeign.setWebhook(telegramConstant.getPath(), telegramConstant.getWebHookPath());
        } catch (Exception _ignored) {
            _ignored.printStackTrace();
        }
    }
}
