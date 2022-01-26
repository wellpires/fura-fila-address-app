package br.com.furafila.addressapp.dto;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.furafila.addressapp.util.Messages;
import br.com.furafila.addressapp.validator.order.FirstOrder;
import br.com.furafila.addressapp.validator.order.SecondOrder;
import br.com.furafila.addressapp.validator.order.ThirdOrder;

@GroupSequence({ NewAddressDTO.class, FirstOrder.class, SecondOrder.class, ThirdOrder.class })
public class NewAddressDTO {

	@NotBlank(message = Messages.CITY_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 5, max = 30, message = Messages.CITY_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String city;

	@NotBlank(message = Messages.DISTRICT_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 5, max = 50, message = Messages.DISTRICT_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String district;

	@NotBlank(message = Messages.POSTAL_ABBREVIATION_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 2, max = 2, message = Messages.POSTAL_ABBREVIATION_IS_NOT_VALID, groups = SecondOrder.class)
	private String postalAbbreviation;

	@NotNull(message = Messages.POSTALCODE_IS_REQUIRED, groups = FirstOrder.class)
	@Min(value = 1001000, message = Messages.POSTALCODE_IS_NOT_VALID, groups = SecondOrder.class)
	@Max(value = 99999999, message = Messages.POSTALCODE_IS_NOT_VALID, groups = ThirdOrder.class)
	private Integer postalCode;

	@NotBlank(message = Messages.ADDRESS_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 5, max = 100, message = Messages.ADDRESS_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String address;

	@NotBlank(message = Messages.ADDRESS_TYPE_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 3, max = 15, message = Messages.ADDRESS_TYPE_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String addressType;

	@NotNull(message = Messages.LATITUDE_IS_REQUIRED)
	private Double latitude;

	@NotNull(message = Messages.LONGITUDE_IS_REQUIRED)
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
