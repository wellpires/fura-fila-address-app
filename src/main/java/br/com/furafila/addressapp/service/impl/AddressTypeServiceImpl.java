package br.com.furafila.addressapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.furafila.addressapp.exception.AddressTypeNotFoundException;
import br.com.furafila.addressapp.model.AddressType;
import br.com.furafila.addressapp.repository.AddressTypeRepository;
import br.com.furafila.addressapp.service.AddressTypeService;

@Service
public class AddressTypeServiceImpl implements AddressTypeService {

	@Autowired
	private AddressTypeRepository addressTypeRepository;

	@Override
	public long findAddressTypeIdByName(String addressType) {

		AddressType addressTypeFound = addressTypeRepository.findIdByName(addressType)
				.orElseThrow(AddressTypeNotFoundException::new);

		return addressTypeFound.getId();
	}

}
