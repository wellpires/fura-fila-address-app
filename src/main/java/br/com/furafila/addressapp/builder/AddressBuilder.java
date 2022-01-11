package br.com.furafila.addressapp.builder;

import br.com.furafila.addressapp.model.Address;
import br.com.furafila.addressapp.model.AddressType;
import br.com.furafila.addressapp.model.District;

public class AddressBuilder {

	private Integer postalCode;
	private String address;
	private Double latitude;
	private Double longitude;
	private AddressType addressType;
	private District district;

	public AddressBuilder postalCode(Integer postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public AddressBuilder address(String address) {
		this.address = address;
		return this;
	}

	public AddressBuilder latitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	public AddressBuilder longitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	public AddressBuilder addressTypeId(long addressTypeId) {
		this.addressType = new AddressType(addressTypeId);
		return this;
	}

	public AddressBuilder districtId(long districtId) {
		this.district = new District(districtId);
		return this;
	}

	public Address build() {
		Address addressBuilded = new Address();
		addressBuilded.setPostalCode(postalCode);
		addressBuilded.setAddress(address);
		addressBuilded.setLatitude(latitude);
		addressBuilded.setLongitude(longitude);
		addressBuilded.setAddressType(addressType);
		addressBuilded.setDistrict(district);

		return addressBuilded;
	}

}
