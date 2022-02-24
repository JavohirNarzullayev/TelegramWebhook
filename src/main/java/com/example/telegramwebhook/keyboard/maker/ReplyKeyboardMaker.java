package com.example.telegramwebhook.keyboard.maker;/*
 @author: Javohir
  Date: 2/21/2022
  Time: 3:47 PM*/

import com.example.telegramwebhook.keyboard.MenuBtn;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Основная клавиатура, расположенная под строкой ввода текста в Telegram
 */
@Component
public class ReplyKeyboardMaker {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(MenuBtn.APPEAL.getButtonUz()));
        row1.add(new KeyboardButton(MenuBtn.APPEAL_ANSWER.getButtonUz()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(MenuBtn.CALL_CENTER.getButtonUz()));
        row2.add(new KeyboardButton(MenuBtn.HELP.getButtonUz()));

        KeyboardRow row3 = new KeyboardRow();

        row3.add(new KeyboardButton(MenuBtn.START.getButtonUz()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getReplyCallCenter(){
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(MenuBtn.GET_MOBILE.getButtonUz(),true,false,new KeyboardButtonPollType()));
        row1.add(new KeyboardButton(MenuBtn.GET_LOCATION.getButtonUz(),false,true,new KeyboardButtonPollType()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(MenuBtn.START.getButtonUz()));
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }
}
