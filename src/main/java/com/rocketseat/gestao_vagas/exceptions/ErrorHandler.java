package com.rocketseat.gestao_vagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    private MessageSource messageSource;

    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorMessageDto>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationErrorMessageDto> dto = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ValidationErrorMessageDto error =  new ValidationErrorMessageDto(message, err.getField());
            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDto> handleUserAlreadyExistsException(AlreadyExistsException e) {
        ErrorMessageDto error = new ErrorMessageDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleUserNotFoundException(NotFoundException e) {
        ErrorMessageDto error = new ErrorMessageDto(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
