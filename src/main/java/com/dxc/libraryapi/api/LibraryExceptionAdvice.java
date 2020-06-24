package com.dxc.libraryapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dxc.libraryapi.exception.BookException;



@RestControllerAdvice
public class LibraryExceptionAdvice
{
	@ExceptionHandler(BookException.class)
    public ResponseEntity<String> handlerBookException(BookException exception)
    {
    	return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
