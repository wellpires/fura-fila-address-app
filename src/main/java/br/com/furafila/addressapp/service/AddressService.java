package br.com.furafila.addressapp.service;

import br.com.furafila.addressapp.dto.FullAddressDTO;
import br.com.furafila.addressapp.dto.NewAddressDTO;

public interface AddressService {

	void save(NewAddressDTO newAddressDTO);

	FullAddressDTO findAddressByPostalCode(Integer postalCode);

}
