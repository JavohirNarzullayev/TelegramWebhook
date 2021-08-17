package com.example.telegramwebhook.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Message;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultTelegram {
    private boolean ok;
    private Message result;
}
