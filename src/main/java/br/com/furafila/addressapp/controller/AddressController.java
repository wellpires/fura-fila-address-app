package br.com.furafila.addressapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.furafila.addressapp.controller.resource.AddressResource;
import br.com.furafila.addressapp.request.NewAddressRequest;
import br.com.furafila.addressapp.service.AddressService;

@RestController
@RequestMapping("address")
public class AddressController implements AddressResource {

	@Autowired
	private AddressService addressService;

	@Override
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> save(@RequestBody NewAddressRequest newAddressRequest) {

		addressService.save(newAddressRequest.getNewAddressDTO());

		return ResponseEntity.noContent().build();
	}

}
