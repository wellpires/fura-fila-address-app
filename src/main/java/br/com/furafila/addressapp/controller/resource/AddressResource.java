package br.com.furafila.addressapp.controller.resource;

import org.springframework.http.ResponseEntity;

import br.com.furafila.addressapp.request.NewAddressRequest;

public interface AddressResource {

	public ResponseEntity<Void> save(NewAddressRequest newAddressRequest);

}
