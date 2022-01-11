package br.com.furafila.addressapp.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.addressapp.dto.NewAddressDTO;

public class NewAddressRequest {

	@JsonProperty("address")
	private NewAddressDTO newAddressDTO;

	public NewAddressDTO getNewAddressDTO() {
		return newAddressDTO;
	}

	public void setNewAddressDTO(NewAddressDTO newAddressDTO) {
		this.newAddressDTO = newAddressDTO;
	}

}
