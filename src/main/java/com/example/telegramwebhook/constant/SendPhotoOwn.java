package com.example.telegramwebhook.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendPhotoOwn {
    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("photo")
    private String photo;
}
