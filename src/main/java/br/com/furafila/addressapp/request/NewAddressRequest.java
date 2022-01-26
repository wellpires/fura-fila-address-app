package br.com.furafila.addressapp.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.addressapp.dto.NewAddressDTO;
import br.com.furafila.addressapp.util.Messages;

public class NewAddressRequest {

	@JsonProperty("address")
	@NotNull(message = Messages.ADDRESS_INFO_IS_REQUIRED)
	@Valid
	private NewAddressDTO newAddressDTO;

	public NewAddressRequest() {
	}

	public NewAddressRequest(NewAddressDTO newAddressDTO) {
		this.newAddressDTO = newAddressDTO;
	}

	public NewAddressDTO getNewAddressDTO() {
		return newAddressDTO;
	}

	public void setNewAddressDTO(NewAddressDTO newAddressDTO) {
		this.newAddressDTO = newAddressDTO;
	}

}
