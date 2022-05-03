package com.example.telegramwebhook.feign;

import feign.FeignException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.IOException;


@Configuration
@Slf4j
public class FeignConfiguration {

    @Bean
    public ErrorDecoder requestInterceptor() {
        return (methodKey, response) -> {
            String requestUrl = response.request().url();
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());

            if (responseStatus.isError()) {
                String format = String.format("Error while calling %s. Status code: %s", requestUrl, responseStatus.value());
                log.error(format);
                return FeignException.errorStatus(methodKey, response);
            }
            return new IOException();
        };
    }

}
