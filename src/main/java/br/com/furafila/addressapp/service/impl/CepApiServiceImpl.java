package br.com.furafila.addressapp.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.furafila.addressapp.dto.CepDTO;
import br.com.furafila.addressapp.service.CepApiService;

@Service
public class CepApiServiceImpl implements CepApiService {

	private static final Logger logger = LoggerFactory.getLogger(CepApiServiceImpl.class);

	@Value("${furafila.api.cep}")
	private String cepApiUrl;

	@Autowired
	private RestTemplate client;

	public CepApiServiceImpl() {
	}

	public CepApiServiceImpl(String cepApiUrl, RestTemplate client) {
		this.cepApiUrl = cepApiUrl;
		this.client = client;
	}

	@Override
	public CepDTO findNewAddress(Integer postalCode) {

		String postalCodePadded = StringUtils.leftPad(postalCode.toString(), 8, '0');

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(cepApiUrl).queryParam("cep", postalCodePadded)
				.queryParam("formato", "json").build();

		CepDTO cepDTO = client.getForEntity(uriComponents.toUriString(), CepDTO.class).getBody();

		if (Integer.parseInt(cepDTO.getResult()) != 1) {
			logger.warn("{} not found in CEP API!", postalCodePadded);
		}

		return cepDTO;
	}

}
