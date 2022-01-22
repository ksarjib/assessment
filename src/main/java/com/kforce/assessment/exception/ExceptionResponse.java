package com.kforce.assessment.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ExceptionResponse {

	private String errorMessage;
	private String requestedURI;

}