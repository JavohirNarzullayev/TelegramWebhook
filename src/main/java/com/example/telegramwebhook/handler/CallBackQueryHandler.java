package com.example.telegramwebhook.handler;

import com.example.telegramwebhook.exceptions.TelegramException;
import com.example.telegramwebhook.feign.TelegramFeign;
import com.example.telegramwebhook.keyboard.BotMessageEnum;
import com.example.telegramwebhook.keyboard.DictionaryResourcePathEnum;
import com.example.telegramwebhook.service.WebhookService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import java.io.IOException;
import static com.example.telegramwebhook.keyboard.CallbackDataPartsEnum.*;

@Component
public record CallBackQueryHandler(TelegramFeign telegramApiClient,
                                   WebhookService webhookService) {


    public void processCallbackQuery(CallbackQuery buttonQuery) throws Exception {
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        switch (data) {
            //Task
            case String category && category.startsWith(TASK_.name()) -> {
                switch (category) {
                    case String child && child.contains(USER_DICTIONARY.name()) -> {
                        var sendMessage = getDictionaryTasks(chatId, chatId, "personal dictionary");
                        webhookService.sendMessageToUser(sendMessage);
                    }
                    case String child && child.contains(ALL_GRADES.name()) -> {
                        var sendMessage = getAllDictionaryTasks(chatId);
                        webhookService.sendMessageToUser(sendMessage);
                    }
                    default -> throw new TelegramException.DefaultException(chatId,"");
                }
            }
            //Dictionary
            case String category && category.startsWith(DICTIONARY_.name()) -> {
                switch (category) {
                    case String child && child.contains(USER_DICTIONARY.name()) -> {
                        var sendMessage = getDictionary(chatId, data);
                        webhookService.sendMessageToUser(sendMessage);
                    }
                    case String child && child.contains(ALL_GRADES.name()) -> {
                        var sendMessage = getAllDefaultDictionaries(chatId);
                        webhookService.sendMessageToUser(sendMessage);
                    }
                    default -> {
                        var sendMessage = getTemplate(chatId);
                        webhookService.sendMessageToUser(sendMessage);
                    }
                }
            }
            default -> {
                var sendMessage = handleDefaultDictionary(chatId, data);
                webhookService.sendMessageToUser(sendMessage);
            }
        }
    }

    private SendMessage handleDefaultDictionary(String chatId, String data) throws IOException {
        DictionaryResourcePathEnum resourcePath = DictionaryResourcePathEnum.valueOf(
                data.substring(TASK_.name().length()));
        return getDictionaryTasks(chatId, resourcePath.name(), resourcePath.getFileName());
    }

    private SendMessage getDictionaryTasks(String chatId, String dictionaryId, String fileName) throws IOException {
        return new SendMessage(chatId, BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
    }

    private SendMessage getAllDictionaryTasks(String chatId) throws IOException {
        return new SendMessage(chatId, BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
    }

    private SendMessage getDictionary(String chatId, String dictionaryId) {
        return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
    }

    private SendMessage getAllDefaultDictionaries(String chatId) throws Exception {
        return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
    }

    private SendMessage getTemplate(String chatId) {
        return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
    }
}
