package br.com.furafila.addressapp.service.impl;

import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
@TestPropertySource("classpath:application.properties")
public class CepApiServiceImplTest {

	@InjectMocks
	private CepApiServiceImpl cepApiService;

	@Mock
	private RestTemplate client;

	@Value("${furafila.api.cep}")
	private String cepApiUrl;

	@BeforeEach
	public void setup() {
		cepApiService = new CepApiServiceImpl(cepApiUrl, client);
	}

	@Test
	public void shouldFindNewAddress() {

		CepDTO cepDTOBody = new CepDTO();
		cepDTOBody.setResult("1");
		cepDTOBody.setPostalAbbreviation("SP");
		cepDTOBody.setCity("Carapicuiba");
		cepDTOBody.setDistrict("Parque Jandaia");
		cepDTOBody.setAddressType("Rua");
		cepDTOBody.setAddress("Newton Macha Júnior");

		ResponseEntity<Object> responseEntity = ResponseEntity.ok(cepDTOBody);
		when(client.getForEntity(anyString(), any())).thenReturn(responseEntity);

		CepDTO cepDTO = cepApiService.findNewAddress(25666852);

		assertNotNull(cepDTO);
		assertThat(cepDTOBody.getResult(), equalToObject(cepDTO.getResult()));

	}

	@Test
	public void shouldNotFindNewAddress() {

		CepDTO cepDTOBody = new CepDTO();
		cepDTOBody.setResult("0");

		ResponseEntity<Object> responseEntity = ResponseEntity.ok(cepDTOBody);
		when(client.getForEntity(anyString(), any())).thenReturn(responseEntity);

		CepDTO cepDTO = cepApiService.findNewAddress(25666852);

		assertNotNull(cepDTO);
		assertThat(cepDTOBody.getResult(), equalToObject(cepDTO.getResult()));

	}

}
