package br.com.furafila.addressapp.exception;

public class CityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4807869732669974008L;

	public CityNotFoundException() {
		super("City not found!");
	}

}
