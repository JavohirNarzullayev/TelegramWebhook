package com.example.telegramwebhook.keyboard.maker;/*
 @author: Javohir
  Date: 2/21/2022
  Time: 3:47 PM*/

import com.example.telegramwebhook.keyboard.CallbackDataPartsEnum;
import com.example.telegramwebhook.keyboard.DictionaryResourcePathEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Клавиатуры, формируемые в ленте Telegram для получения файлов
 */
@Component
public class InlineKeyboardMaker {

    public InlineKeyboardMarkup getInlineMessageButtonsWithTemplate(String prefix, boolean isUserDictionaryNeed) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getInlineMessageButtons(prefix, isUserDictionaryNeed);
        inlineKeyboardMarkup.getKeyboard().add(getButton(
                "Шаблон",
                prefix + CallbackDataPartsEnum.TEMPLATE.name()
        ));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageButtons(String prefix, boolean isUserDictionaryNeed) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (DictionaryResourcePathEnum dictionary : DictionaryResourcePathEnum.values()) {
            rowList.add(getButton(
                    dictionary.getButtonName(),
                    prefix + dictionary.name()
            ));
        }

        if (!rowList.isEmpty()) {
            rowList.add(getButton(
                    "Все элементы",
                    prefix + CallbackDataPartsEnum.ALL_GRADES.name()
            ));
        }

        if (isUserDictionaryNeed) {
            rowList.add(getButton(
                    "Ваш элементы",
                    prefix + CallbackDataPartsEnum.USER_DICTIONARY.name()
            ));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
