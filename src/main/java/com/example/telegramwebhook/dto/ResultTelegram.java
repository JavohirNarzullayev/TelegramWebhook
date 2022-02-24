package com.example.telegramwebhook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultTelegram<T> {
    private boolean ok;
    private T result;
}
