package br.com.furafila.addressapp.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.furafila.addressapp.dto.CepDTO;
import br.com.furafila.addressapp.dto.GeoCodeResultDTO;
import br.com.furafila.addressapp.dto.GeometryDTO;
import br.com.furafila.addressapp.dto.LocationDTO;
import br.com.furafila.addressapp.response.GeoCodeResponse;
import br.com.furafila.addressapp.service.GoogleApiService;

@Service
public class GoogleApiServiceImpl implements GoogleApiService {

	private static final Logger logger = LoggerFactory.getLogger(GoogleApiServiceImpl.class);

	@Value("${furafila.api.google.geo-code}")
	private String geoCodeUrl;

	@Value("${furafila.google.api-key}")
	private String googleApiKey;

	@Autowired
	private RestTemplate client;

	public GoogleApiServiceImpl() {
	}

	public GoogleApiServiceImpl(String geoCodeUrl, String googleApiKey, RestTemplate client) {
		this.geoCodeUrl = geoCodeUrl;
		this.googleApiKey = googleApiKey;
		this.client = client;

	}

	@Override
	public LocationDTO findGeoCode(CepDTO newAddress) {

		String urlEncoded = "";
		try {
			String addressFormatted = String.format("%s,%s,%s,%s,BR", newAddress.getAddress(), newAddress.getDistrict(),
					newAddress.getCity(), newAddress.getPostalAbbreviation());
			urlEncoded = URLEncoder.encode(addressFormatted, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(geoCodeUrl).queryParam("address", urlEncoded)
				.queryParam("key", googleApiKey).build();

		ResponseEntity<GeoCodeResponse> response = client.getForEntity(uriComponents.toUriString(),
				GeoCodeResponse.class);

		LocationDTO locationDTO = new LocationDTO();
		if (response.getStatusCode().is2xxSuccessful()) {
			GeometryDTO geometryDTO = response.getBody().getGeoCodeResultDTO().stream().findFirst()
					.orElseGet(GeoCodeResultDTO::new).getGeometryDTO();

			if (Objects.nonNull(geometryDTO)) {
				locationDTO = geometryDTO.getLocationDTO();
			}

		}

		return locationDTO;
	}

}
