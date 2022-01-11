package br.com.furafila.addressapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.addressapp.dto.FullAddressDTO;

public class FullAddressResponse {

	@JsonProperty("address")
	private FullAddressDTO fullAddressDTO;

	public FullAddressResponse(FullAddressDTO fullAddressDTO) {
		this.fullAddressDTO = fullAddressDTO;
	}

	public FullAddressDTO getFullAddressDTO() {
		return fullAddressDTO;
	}

	public void setFullAddressDTO(FullAddressDTO fullAddressDTO) {
		this.fullAddressDTO = fullAddressDTO;
	}

}
