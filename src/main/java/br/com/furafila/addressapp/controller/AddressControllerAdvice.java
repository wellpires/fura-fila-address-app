package br.com.furafila.addressapp.controller;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.furafila.addressapp.exception.AddressTypeNotFoundException;
import br.com.furafila.addressapp.exception.CityNotFoundException;
import br.com.furafila.addressapp.exception.ServerErrorApiException;
import br.com.furafila.addressapp.exception.StateNotFoundException;
import br.com.furafila.addressapp.response.ErrorResponse;

@RestControllerAdvice
public class AddressControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(AddressControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleException(Exception ex) {
		logger.error(ex.getMessage(), ex);

		return ResponseEntity.internalServerError().build();
	}

	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<Void> handleCityNotFoundException(CityNotFoundException cex) {
		logger.error(cex.getMessage(), cex);

		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(StateNotFoundException.class)
	public ResponseEntity<Void> handleStateNotFoundException(StateNotFoundException snex) {
		logger.error(snex.getMessage(), snex);

		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(AddressTypeNotFoundException.class)
	public ResponseEntity<Void> handleAddressTypeNotFoundException(AddressTypeNotFoundException atex) {
		logger.error(atex.getMessage(), atex);

		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(ServerErrorApiException.class)
	public ResponseEntity<Void> handleServerErrorApiException(ServerErrorApiException seEx) {
		logger.error(seEx.getMessage(), seEx);

		return ResponseEntity.internalServerError().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException maEx) {

		String rejectedValue = maEx.getBindingResult().getFieldErrors().stream().filter(Objects::nonNull).findFirst()
				.map(item -> String.valueOf(item.getRejectedValue())).orElseGet(() -> StringUtils.EMPTY);
		String defaultMessage = maEx.getBindingResult().getFieldError().getDefaultMessage();
		logger.error("{} - Value: {}", defaultMessage, rejectedValue);

		return ResponseEntity.badRequest().body(new ErrorResponse(defaultMessage));
	}

}
