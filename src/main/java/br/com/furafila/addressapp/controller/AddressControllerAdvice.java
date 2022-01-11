package br.com.furafila.addressapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.furafila.addressapp.exception.AddressTypeNotFoundException;
import br.com.furafila.addressapp.exception.CityNotFoundException;
import br.com.furafila.addressapp.exception.ServerErrorApiException;
import br.com.furafila.addressapp.exception.StateNotFoundException;

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

}
