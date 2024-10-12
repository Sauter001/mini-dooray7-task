package com.nhnacademy.taskapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
//        @ExceptionHandler(AccountAlreadyExistException.class)
//        public ResponseEntity<ErrorDto> accountAlreadyExistException(AccountAlreadyExistException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(
//                    new ErrorDto(409, e.getMessage())
//            );
//        }
//
//        @ExceptionHandler(AccountNotFoundException.class)
//        public ResponseEntity<ErrorDto> accountNotFoundException(AccountNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ErrorDto(404, e.getMessage())
//            );
//        }
//    }
}
