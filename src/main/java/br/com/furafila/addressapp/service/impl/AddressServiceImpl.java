package br.com.furafila.addressapp.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.furafila.addressapp.builder.AddressBuilder;
import br.com.furafila.addressapp.dto.NewAddressDTO;
import br.com.furafila.addressapp.model.Address;
import br.com.furafila.addressapp.repository.AddressRepository;
import br.com.furafila.addressapp.service.AddressService;
import br.com.furafila.addressapp.service.AddressTypeService;
import br.com.furafila.addressapp.service.CityService;
import br.com.furafila.addressapp.service.DistrictService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private CityService cityService;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private AddressTypeService addressTypeService;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public void save(NewAddressDTO newAddressDTO) {

		long cityId = cityService.createCity(newAddressDTO.getCity(), newAddressDTO.getPostalAbbreviation());

		long districtId = districtService.createDistrict(newAddressDTO.getDistrict(), cityId);

		long addressTypeId = addressTypeService.findAddressTypeIdByName(newAddressDTO.getAddressType());

		Address address = addressRepository.findByPostalCodeAndAddressIgnoreCase(newAddressDTO.getPostalCode(),
				newAddressDTO.getAddress());

		if (Objects.isNull(address)) {
			Address newAddress = new AddressBuilder().postalCode(newAddressDTO.getPostalCode())
					.address(newAddressDTO.getAddress()).latitude(newAddressDTO.getLatitude())
					.longitude(newAddressDTO.getLongitude()).addressTypeId(addressTypeId).districtId(districtId)
					.build();

			addressRepository.save(newAddress);
		}

	}

}
