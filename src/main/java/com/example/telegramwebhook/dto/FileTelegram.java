package com.example.telegramwebhook.dto;/*
 @author: Javohir
  Date: 2/22/2022
  Time: 2:19 PM*/

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileTelegram {
    @JsonProperty("file_id")
    private String file_id;

    @JsonProperty("file_unique_id")
    private String file_unique_id;

    @JsonProperty("file_size")
    private Integer file_size;

    @JsonProperty("file_path")
    private String file_path;

}
