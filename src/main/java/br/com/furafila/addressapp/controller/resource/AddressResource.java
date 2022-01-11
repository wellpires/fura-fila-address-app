package br.com.furafila.addressapp.controller.resource;

import org.springframework.http.ResponseEntity;

import br.com.furafila.addressapp.request.NewAddressRequest;
import br.com.furafila.addressapp.response.FullAddressResponse;

public interface AddressResource {

	public ResponseEntity<Void> save(NewAddressRequest newAddressRequest);

	public ResponseEntity<FullAddressResponse> findAddress(Integer postalCode);
	
}
