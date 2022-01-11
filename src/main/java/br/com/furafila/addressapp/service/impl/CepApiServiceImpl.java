package br.com.furafila.addressapp.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.furafila.addressapp.dto.CepDTO;
import br.com.furafila.addressapp.service.CepApiService;

@Service
public class CepApiServiceImpl implements CepApiService {

	@Value("${furafila.api.cep}")
	private String cepApiUrl;

	@Autowired
	private RestTemplate client;

	@Override
	public CepDTO findNewAddress(Integer postalCode) {

		String postalCodePadded = StringUtils.leftPad(postalCode.toString(), 8, '0');

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(cepApiUrl).queryParam("cep", postalCodePadded)
				.queryParam("formato", "json").build();

		ResponseEntity<CepDTO> response = client.getForEntity(uriComponents.toUriString(), CepDTO.class);

		return response.getBody();
	}

}
