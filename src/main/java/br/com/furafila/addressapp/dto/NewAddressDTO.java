package br.com.furafila.addressapp.dto;

public class NewAddressDTO {

	private String city;
	private String district;
	private String postalAbbreviation;
	private Integer postalCode;
	private String address;
	private String addressType;
	private Double latitude;
	private Double longitude;

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

	public String getPostalAbbreviation() {
		return postalAbbreviation;
	}

	public void setPostalAbbreviation(String postalAbbreviation) {
		this.postalAbbreviation = postalAbbreviation;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
