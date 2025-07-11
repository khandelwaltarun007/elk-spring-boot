package com.javalabs.userserviceelkstack.exception;

import java.time.LocalDate;

public class ExceptionResponseEntity {

	private LocalDate timestamp;
	private String message;
	private String details;

	public ExceptionResponseEntity(LocalDate timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}