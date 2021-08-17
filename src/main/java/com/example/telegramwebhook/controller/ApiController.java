package com.example.telegramwebhook.controller;

import com.example.telegramwebhook.service.TgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController("/api/telegram")
@RequiredArgsConstructor
public class ApiController {
    private final TgService tgService;
    @PostMapping
    public void getUpdate(@RequestBody Update update){
         tgService.updateWait(update);
    }
}
