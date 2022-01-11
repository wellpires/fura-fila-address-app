package br.com.furafila.addressapp.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorApiException extends RuntimeException {

	private static final long serialVersionUID = -361826878162465768L;

	public ServerErrorApiException(String path, HttpStatus statusCode) {
		super(String.format("%s - %s - [%s]", statusCode.value(), statusCode.getReasonPhrase(), path));
	}

}
