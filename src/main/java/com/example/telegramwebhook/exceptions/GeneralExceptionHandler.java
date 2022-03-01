package com.example.telegramwebhook.exceptions;


import com.example.telegramwebhook.service.WebhookService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    private final WebhookService webhookService;
    @Autowired
    public GeneralExceptionHandler(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TelegramException.TooBigMessageException.class)
    public ResponseEntity<?> toBigMessage(TelegramException.TooBigMessageException exception) {
        sendError(exception.getChatId(), exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());
        errors.put("chat_id", exception.getChatId());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TelegramException.TelegramFileNotFoundException.class)
    public ResponseEntity<?> fileNotFound(TelegramException.TelegramFileNotFoundException exception) {
        sendError(exception.getChatId(), exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());
        errors.put("chat_id", exception.getChatId());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TelegramException.InternalErrorException.class)
    public ResponseEntity<?> internalError(TelegramException.InternalErrorException exception) {
        sendError(exception.getChatId(), exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());
        errors.put("chat_id", exception.getChatId());
        return new ResponseEntity<>(errors, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(TelegramException.CommandEmptyException.class)
    public ResponseEntity<?> commandEmpty(TelegramException.CommandEmptyException exception) {
        sendError(exception.getChatId(), exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());
        errors.put("chat_id", exception.getChatId());
        return new ResponseEntity<>(errors, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(TelegramException.DefaultException.class)
    public ResponseEntity<?> defaultError(TelegramException.DefaultException exception) {
        sendError(exception.getChatId(), exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());
        errors.put("chat_id", exception.getChatId());
        return new ResponseEntity<>(errors, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> feignError(TelegramException.DefaultException exception) {
        sendError(exception.getChatId(), exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());
        errors.put("chat_id", exception.getChatId());
        return new ResponseEntity<>(errors, HttpStatus.BAD_GATEWAY);
    }

    private void sendError(String exception, String exception1) {
        webhookService.sendMessageToUser(
                new SendMessage(exception, exception1)
        );
    }


}
