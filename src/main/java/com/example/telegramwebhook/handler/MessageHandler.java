package com.example.telegramwebhook.handler;

import com.example.telegramwebhook.constant.TelegramConstant;
import com.example.telegramwebhook.dto.SendPhotoOwn;
import com.example.telegramwebhook.exceptions.TooBigMessageException;
import com.example.telegramwebhook.exceptions.TelegramFileNotFoundException;
import com.example.telegramwebhook.keyboard.BotMessageEnum;
import com.example.telegramwebhook.keyboard.CallbackDataPartsEnum;
import com.example.telegramwebhook.keyboard.maker.InlineKeyboardMaker;
import com.example.telegramwebhook.keyboard.maker.ReplyKeyboardMaker;
import com.example.telegramwebhook.service.WebhookService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static com.example.telegramwebhook.keyboard.MenuBtn.findByText;

@Component
public record MessageHandler(WebhookService webhookService,
                             ReplyKeyboardMaker replyKeyboardMaker,
                             InlineKeyboardMaker inlineKeyboardMaker,
                             TelegramConstant telegramConstant) {


    public void answerMessage(Message message) throws TelegramApiRequestException {
        String chatId = message.getChatId().toString();
        if (message.hasDocument()) {
            addUserDictionary(chatId,message.getDocument());
        }
        if (message.hasContact()) {
            System.out.println(message.getContact().toString());
            return;
        }
        if (message.hasLocation()) {
            System.out.println(message.getLocation().toString());
            return;
        }
        var file = webhookService.getFile("BQACAgIAAxkBAAIGcmIXd4vTxyAQrd4TmH8pLPbGj5XqAAIjFwACTIa5SHZaBSVqYB6tIwQ");
        webhookService.sendDocument(chatId,file.getResult().getFile_id(),"2-ВАРИАНТ.doc",ParseMode.HTML);

        switch (findByText(message.getText())) {
            case START -> getStartMessage(chatId);
            case APPEAL -> getTasksMessage(chatId);
            case APPEAL_ANSWER -> getDictionaryMessage(chatId);
            case CALL_CENTER -> {
                var sendMessage = new SendMessage(chatId, BotMessageEnum.CALL_CENTER.getMessage());
                sendMessage.enableMarkdown(false);
                sendMessage.setReplyMarkup(replyKeyboardMaker.getReplyCallCenter());
                sendMessage.setParseMode(ParseMode.HTML);
                webhookService.sendMessageToUser(sendMessage);
            }
            case HELP -> {
                SendMessage helpMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
                webhookService.sendMessageToUser(helpMessage);
            }
            case null -> throw new IllegalArgumentException();
            default -> {
                webhookService.sendMessageToUser(new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage()));
            }
        }
    }

    private void getStartMessage(String chatId) throws TelegramApiRequestException {
        var sendPhotoOwn = SendPhotoOwn.builder()
                .chatId(chatId)
                .caption("Asosiy mavzu")
                .photo("https://instagram.ftas1-1.fna.fbcdn.net/v/t51.2885-15/sh0.08/e35/s640x640/243971681_323123446238142_4302249588099074604_n.webp.jpg?_nc_ht=instagram.ftas1-1.fna.fbcdn.net&_nc_cat=106&_nc_ohc=np81M2t1oAMAX9hrI-4&edm=ABfd0MgBAAAA&ccb=7-4&oh=00_AT9KsEVUX77EGKHuhifZ2TDM0ITojVzsSdT8VYDciRNYYg&oe=621B2749&_nc_sid=7bff83").build();
        if (!webhookService.sendPhotoToUser(sendPhotoOwn).isOk())
            throw new TelegramApiRequestException("Webhook not working!!");

        var sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(false);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        sendMessage.setParseMode(ParseMode.HTML);
        webhookService.sendMessageToUser(sendMessage);
    }

    private void getTasksMessage(String chatId) {
        var sendMessage = new SendMessage(chatId, BotMessageEnum.CHOOSE_COMMAND_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtons(
                CallbackDataPartsEnum.TASK_.name(),
                true
        ));
        webhookService.sendMessageToUser(sendMessage);
    }

    private void getDictionaryMessage(String chatId) {
        var sendMessage = new SendMessage(chatId, BotMessageEnum.CHOOSE_COMMAND_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtonsWithTemplate(
                CallbackDataPartsEnum.DICTIONARY_.name(),
                true
        ));
        webhookService.sendMessageToUser(sendMessage);
    }

    private void addUserDictionary(String chatId, Document document) {
        try {
            //logic and trow
            if (document.getFileSize()>=20_000_000) throw new TooBigMessageException();
            webhookService.sendMessageToUser(new SendMessage(chatId, BotMessageEnum.SUCCESS_UPLOAD_MESSAGE.getMessage()));
        } catch (TelegramFileNotFoundException e) {
            webhookService.sendMessageToUser(new SendMessage(chatId, BotMessageEnum.EXCEPTION_TELEGRAM_API_MESSAGE.getMessage()));
        } catch (TooBigMessageException e) {
            webhookService.sendMessageToUser(new SendMessage(chatId, BotMessageEnum.EXCEPTION_TOO_LARGE_MESSAGE.getMessage()));
        } catch (Exception e) {
            webhookService.sendMessageToUser(new SendMessage(chatId, BotMessageEnum.EXCEPTION_BAD_FILE_MESSAGE.getMessage()));
        }
    }
}
