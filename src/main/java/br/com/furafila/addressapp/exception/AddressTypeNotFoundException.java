package br.com.furafila.addressapp.exception;

public class AddressTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2906915130029751998L;

	public AddressTypeNotFoundException() {
		super("Address Type not found!");
	}

}
