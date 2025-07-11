package com.javalabs.userserviceelkstack.exception;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.javalabs.userserviceelkstack.exception.entity.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false),
														HttpStatus.NOT_FOUND.value());
		ex.printStackTrace();
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.add("Validation Error in " + fieldError.getField() + " : " + fieldError.getDefaultMessage());
		}
		for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
			errors.add(objectError.getObjectName() + " : " + objectError.getDefaultMessage());
		}

		ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), "Validation Failed", String.join("; ", errors),
				HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), "Internal Server Error",
				ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
