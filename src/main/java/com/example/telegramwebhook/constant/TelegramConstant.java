package com.example.telegramwebhook.constant;/* 
 @author: Javohir
  Date: 2/21/2022
  Time: 2:41 PM*/

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramConstant {
    @Value("${telegram.webhook-path}")
    String webHookPath;
    @Value("${telegram.user}")
    String userName;
    @Value("${telegram.token}")
    String botToken;


     TreeMap<String,String> map=new TreeMap<>();

    public String getPath(){
        return "bot"+this.botToken;
    }

    public void add(String chatId,String fileId){
        map.put(chatId,fileId);
    };

    public String  get(String chatId){
        return map.get(chatId);
    };

}
