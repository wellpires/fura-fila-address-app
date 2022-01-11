package br.com.furafila.addressapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodeResultDTO {

	@JsonProperty("geometry")
	private GeometryDTO geometryDTO;

	public GeometryDTO getGeometryDTO() {
		return geometryDTO;
	}

	public void setGeometryDTO(GeometryDTO geometryDTO) {
		this.geometryDTO = geometryDTO;
	}

}
