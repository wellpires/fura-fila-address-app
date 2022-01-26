package br.com.furafila.addressapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import br.com.furafila.addressapp.dto.CepDTO;
import br.com.furafila.addressapp.dto.GeoCodeResultDTO;
import br.com.furafila.addressapp.dto.GeometryDTO;
import br.com.furafila.addressapp.dto.LocationDTO;
import br.com.furafila.addressapp.response.GeoCodeResponse;
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
@TestPropertySource("classpath:application.properties")
class GoogleApiServiceImplTest {

	@InjectMocks
	private GoogleApiServiceImpl googleApiService;

	@Mock
	private RestTemplate client;

	@Value("${furafila.api.google.geo-code}")
	private String geoCodeUrl;

	@Value("${furafila.google.api-key}")
	private String googleApiKey;

	@BeforeEach
	public void setup() {
		this.googleApiService = new GoogleApiServiceImpl(geoCodeUrl, googleApiKey, client);
	}

	@Test
	void shouldFindGeoCodeWithSuccess() {

		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setLatitude(-45.00054);
		locationDTO.setLongitude(-47.00054);

		GeometryDTO geometryDTO = new GeometryDTO();
		geometryDTO.setLocationDTO(locationDTO);

		GeoCodeResultDTO geoCodeResultDTO1 = new GeoCodeResultDTO();
		geoCodeResultDTO1.setGeometryDTO(geometryDTO);

		GeoCodeResponse geoCodeResponse = new GeoCodeResponse();
		geoCodeResponse.setGeoCodeResultDTO(Arrays.asList(geoCodeResultDTO1));
		ResponseEntity<Object> responseEntity = ResponseEntity.ok(geoCodeResponse);
		when(client.getForEntity(anyString(), any())).thenReturn(responseEntity);

		CepDTO newAddress = new CepDTO();
		newAddress.setAddress("dos Autonomistas");
		newAddress.setDistrict("Vila Yara");
		newAddress.setCity("Osasco");
		newAddress.setPostalAbbreviation("SP");

		LocationDTO locationDTOFound = googleApiService.findGeoCode(newAddress);

		assertNotNull(locationDTOFound);
		assertNotNull(locationDTOFound.getLatitude());
		assertNotNull(locationDTOFound.getLongitude());

	}

	@Test
	void shouldNotFindGeoCodeBecauseStatusCodeIsnt2xx() {

		ResponseEntity<Object> responseEntity = ResponseEntity.internalServerError().build();
		when(client.getForEntity(anyString(), any())).thenReturn(responseEntity);

		CepDTO newAddress = new CepDTO();
		newAddress.setAddress("dos Autonomistas");
		newAddress.setDistrict("Vila Yara");
		newAddress.setCity("Osasco");
		newAddress.setPostalAbbreviation("SP");

		LocationDTO locationDTOFound = googleApiService.findGeoCode(newAddress);

		assertNotNull(locationDTOFound);
		assertNull(locationDTOFound.getLatitude());
		assertNull(locationDTOFound.getLongitude());

	}

	@Test
	void shouldNotFindGeoCodeBecauseGeoCodeResultIsEmpty() {

		GeoCodeResponse geoCodeResponse = new GeoCodeResponse();
		geoCodeResponse.setGeoCodeResultDTO(Arrays.asList());
		ResponseEntity<Object> responseEntity = ResponseEntity.ok(geoCodeResponse);
		when(client.getForEntity(anyString(), any())).thenReturn(responseEntity);

		CepDTO newAddress = new CepDTO();
		newAddress.setAddress("dos Autonomistas");
		newAddress.setDistrict("Vila Yara");
		newAddress.setCity("Osasco");
		newAddress.setPostalAbbreviation("SP");

		LocationDTO locationDTOFound = googleApiService.findGeoCode(newAddress);

		assertNotNull(locationDTOFound);
		assertNull(locationDTOFound.getLatitude());
		assertNull(locationDTOFound.getLongitude());

	}

}
