package br.com.furafila.addressapp.controller.resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.furafila.addressapp.request.NewAddressRequest;
import br.com.furafila.addressapp.response.FullAddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface AddressResource {

	@Operation(summary = "Create new address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "New address created", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	public ResponseEntity<Void> save(NewAddressRequest newAddressRequest);

	@Operation(summary = "Find full address by postal code")
	@ApiResponse(responseCode = "200", description = "Found the address", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FullAddressResponse.class)) })
	public ResponseEntity<FullAddressResponse> findAddress(Integer postalCode);

}
