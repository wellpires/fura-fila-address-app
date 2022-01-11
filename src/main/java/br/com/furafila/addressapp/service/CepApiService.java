package br.com.furafila.addressapp.service;

import br.com.furafila.addressapp.dto.CepDTO;

public interface CepApiService {

	CepDTO findNewAddress(Integer postalCode);

}
