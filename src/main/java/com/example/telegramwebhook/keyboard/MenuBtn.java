package com.example.telegramwebhook.keyboard;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum MenuBtn {
    START("Ortga","/start"),
    DEFAULT("","/default"),


    APPEAL("Murojaat jo'natish", "/send_appeal"),
    APPEAL_ANSWER("Murojaat javobini tekshirish", "/check_appeal"),
    CALL_CENTER("Call-center markazi", "/call-center"),
    HELP("Ma'lumot", "/info"),

    GET_MOBILE("Raqam jo'natish", "/get-mobile"),
    GET_LOCATION("Joylashuv joyini tashlash", "/get-location");

    private final String buttonUz;
    private final String command;
    private static final Map<String, MenuBtn> stringMap = new HashMap<>();

    static {
        for (MenuBtn value : values()) {
            stringMap.put(value.getButtonUz(), value);
            stringMap.put(value.getCommand(), value);
        }
    }



    public static MenuBtn findByText(String text){
        return stringMap.getOrDefault(text,DEFAULT);
    }}




