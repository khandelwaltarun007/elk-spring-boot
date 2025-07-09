package com.javalabs.userserviceelkstack.exception.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
	private LocalDate timestamp;
	private String message;
	private String details;
	private Integer httpStatus;

}
