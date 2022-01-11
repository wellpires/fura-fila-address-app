package br.com.furafila.addressapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "logradouro")
public class Address implements Serializable {

	private static final long serialVersionUID = -6641679633842924842L;

	@Id
	@Column(name = "nroCep")
	private Integer postalCode;

	@Column(name = "logradouro")
	private String address;

	@Column(name = "longitude", columnDefinition = "numeric")
	private Double longitude;

	@Column(name = "latitude", columnDefinition = "numeric")
	private Double latitude;

	@ManyToOne
	@JoinColumn(name = "id_tipo_logradouro_FK", columnDefinition = "int4")
	private AddressType addressType;

	@ManyToOne
	@JoinColumn(name = "id_bairro_FK", columnDefinition = "int4")
	private District district;

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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

}
