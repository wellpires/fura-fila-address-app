package br.com.furafila.addressapp.service;

import br.com.furafila.addressapp.dto.CepDTO;
import br.com.furafila.addressapp.dto.LocationDTO;

public interface GoogleApiService {

	LocationDTO findGeoCode(CepDTO newAddress);

}
