package br.com.furafila.addressapp.exception;

public class StateNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7979222128525490154L;

	public StateNotFoundException() {
		super("State not found!");
	}

}
