package br.com.furafila.addressapp.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.addressapp.dto.GeoCodeResultDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodeResponse {

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JsonProperty("results")
	private List<GeoCodeResultDTO> geoCodeResultDTO;

	public List<GeoCodeResultDTO> getGeoCodeResultDTO() {
		return geoCodeResultDTO;
	}

	public void setGeoCodeResultDTO(List<GeoCodeResultDTO> geoCodeResultDTO) {
		this.geoCodeResultDTO = geoCodeResultDTO;
	}

}
