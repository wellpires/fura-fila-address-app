package br.com.furafila.addressapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CepDTO {

	@JsonProperty("resultado")
	private String result;

	@JsonProperty("uf")
	private String postalAbbreviation;

	@JsonProperty("cidade")
	private String city;

	@JsonProperty("bairro")
	private String district;

	@JsonProperty("tipo_logradouro")
	private String addressType;

	@JsonProperty("logradouro")
	private String address;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPostalAbbreviation() {
		return postalAbbreviation;
	}

	public void setPostalAbbreviation(String postalAbbreviation) {
		this.postalAbbreviation = postalAbbreviation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
