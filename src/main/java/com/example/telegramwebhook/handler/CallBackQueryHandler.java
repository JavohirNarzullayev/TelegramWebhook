package com.example.telegramwebhook.handler;

import com.example.telegramwebhook.exceptions.UserDictionaryNotFoundException;
import com.example.telegramwebhook.feign.TelegramFeign;
import com.example.telegramwebhook.keyboard.BotMessageEnum;
import com.example.telegramwebhook.keyboard.CallbackDataPartsEnum;
import com.example.telegramwebhook.keyboard.DictionaryResourcePathEnum;
import com.example.telegramwebhook.service.WebhookService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.IOException;

@Component
public record CallBackQueryHandler(TelegramFeign telegramApiClient,
                                   WebhookService webhookService)  {


    public void processCallbackQuery(CallbackQuery buttonQuery) throws IOException {
        final String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();

        if (data.equals(CallbackDataPartsEnum.TASK_.name() + CallbackDataPartsEnum.USER_DICTIONARY.name())) {
            var sendMessage = getDictionaryTasks(chatId, chatId, "personal dictionary");
            webhookService.sendMessageToUser(sendMessage);
            return;
        } else if (data.equals(CallbackDataPartsEnum.TASK_.name() + CallbackDataPartsEnum.ALL_GRADES.name())) {
            var sendMessage = getAllDictionaryTasks(chatId);
            webhookService.sendMessageToUser(sendMessage);
            return;
        } else if (data.equals(CallbackDataPartsEnum.DICTIONARY_.name() + CallbackDataPartsEnum.USER_DICTIONARY.name())) {
            var sendMessage = getDictionary(chatId, data);
            webhookService.sendMessageToUser(sendMessage);
            return;
        } else if (data.equals(CallbackDataPartsEnum.DICTIONARY_.name() + CallbackDataPartsEnum.ALL_GRADES.name())) {
            var sendMessage = getAllDefaultDictionaries(chatId);
            webhookService.sendMessageToUser(sendMessage);
            return;
        } else if (data.equals(CallbackDataPartsEnum.DICTIONARY_.name() + CallbackDataPartsEnum.TEMPLATE.name())) {
            var sendMessage = getTemplate(chatId);
            webhookService.sendMessageToUser(sendMessage);
            return;
        } else {
            var sendMessage = handleDefaultDictionary(chatId, data);
            webhookService.sendMessageToUser(sendMessage);
            return;
        }
    }

    private SendMessage handleDefaultDictionary(String chatId, String data) throws IOException {
        if (data.startsWith(CallbackDataPartsEnum.TASK_.name())) {
            DictionaryResourcePathEnum resourcePath = DictionaryResourcePathEnum.valueOf(
                    data.substring(CallbackDataPartsEnum.TASK_.name().length())
            );
            return getDictionaryTasks(chatId, resourcePath.name(), resourcePath.getFileName());
        } else if (data.startsWith(CallbackDataPartsEnum.DICTIONARY_.name())) {
            return getDictionary(chatId, data.substring(CallbackDataPartsEnum.DICTIONARY_.name().length()));
        } else {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_BAD_BUTTON_NAME_MESSAGE.getMessage());
        }
    }

    private SendMessage getDictionaryTasks(String chatId, String dictionaryId, String fileName) throws IOException {
        try {
            var sendMessage = new SendMessage(chatId, BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
            return sendMessage;
        } catch (Exception e) {
            var sendMessage = new SendMessage(chatId, BotMessageEnum.EXCEPTION_TASKS_WTF_MESSAGE.getMessage());
            return sendMessage;
        }
    }

    private SendMessage getAllDictionaryTasks(String chatId) throws IOException {
        try {
            var sendMessage = new SendMessage(chatId, BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
            return sendMessage;
        } catch (Exception e) {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_TASKS_WTF_MESSAGE.getMessage());
        }
    }

    private SendMessage getDictionary(String chatId, String dictionaryId) {
        try {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
        } catch (UserDictionaryNotFoundException e) {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
        } catch (Exception e) {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_DICTIONARY_WTF_MESSAGE.getMessage());
        }
    }

    private SendMessage getAllDefaultDictionaries(String chatId) {
        try {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
        } catch (UserDictionaryNotFoundException e) {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
        } catch (Exception e) {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_DICTIONARY_WTF_MESSAGE.getMessage());
        }
    }

    private SendMessage getTemplate(String chatId) {
        try {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_NOT_FOUND_MESSAGE.getMessage());
        } catch (Exception e) {
            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_TEMPLATE_WTF_MESSAGE.getMessage());
        }
    }
}
