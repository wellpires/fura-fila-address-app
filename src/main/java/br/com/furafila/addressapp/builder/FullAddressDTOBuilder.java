package br.com.furafila.addressapp.builder;

import br.com.furafila.addressapp.dto.FullAddressDTO;

public class FullAddressDTOBuilder {

	private Integer postalCode;
	private String addressType;
	private String address;
	private String district;
	private String city;
	private String postalAbbreviation;
	private Double latitude;
	private Double longitude;

	public FullAddressDTOBuilder postalCode(Integer postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public FullAddressDTOBuilder addressType(String addressType) {
		this.addressType = addressType;
		return this;
	}

	public FullAddressDTOBuilder address(String address) {
		this.address = address;
		return this;
	}

	public FullAddressDTOBuilder district(String district) {
		this.district = district;
		return this;
	}

	public FullAddressDTOBuilder city(String city) {
		this.city = city;
		return this;
	}

	public FullAddressDTOBuilder postalAbbreviation(String postalAbbreviation) {
		this.postalAbbreviation = postalAbbreviation;
		return this;
	}

	public FullAddressDTOBuilder latitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	public FullAddressDTOBuilder longitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	public FullAddressDTO build() {
		FullAddressDTO fullAddressDTO = new FullAddressDTO();

		fullAddressDTO.setPostalCode(postalCode);
		fullAddressDTO.setAddressType(addressType);
		fullAddressDTO.setAddress(address);
		fullAddressDTO.setDistrict(district);
		fullAddressDTO.setCity(city);
		fullAddressDTO.setPostalAbbreviation(postalAbbreviation);
		fullAddressDTO.setLongitude(longitude);
		fullAddressDTO.setLatitude(latitude);

		return fullAddressDTO;
	}

}
