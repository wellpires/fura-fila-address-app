package br.com.furafila.addressapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDTO {

	@JsonProperty("lat")
	private Double latitude;

	@JsonProperty("lng")
	private Double longitude;

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
