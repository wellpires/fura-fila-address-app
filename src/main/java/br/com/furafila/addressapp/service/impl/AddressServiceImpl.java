package br.com.furafila.addressapp.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.furafila.addressapp.builder.AddressBuilder;
import br.com.furafila.addressapp.builder.FullAddressDTOBuilder;
import br.com.furafila.addressapp.dto.CepDTO;
import br.com.furafila.addressapp.dto.FullAddressDTO;
import br.com.furafila.addressapp.dto.LocationDTO;
import br.com.furafila.addressapp.dto.NewAddressDTO;
import br.com.furafila.addressapp.model.Address;
import br.com.furafila.addressapp.repository.AddressRepository;
import br.com.furafila.addressapp.service.AddressService;
import br.com.furafila.addressapp.service.AddressTypeService;
import br.com.furafila.addressapp.service.CepApiService;
import br.com.furafila.addressapp.service.CityService;
import br.com.furafila.addressapp.service.DistrictService;
import br.com.furafila.addressapp.service.GoogleApiService;

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

	@Autowired
	private CepApiService cepApiService;

	@Autowired
	private GoogleApiService googleApiService;

	@Override
	public void save(NewAddressDTO newAddressDTO) {

		Address address = addressRepository.findByPostalCodeAndAddressIgnoreCase(newAddressDTO.getPostalCode(),
				newAddressDTO.getAddress());

		if (Objects.nonNull(address)) {
			return;
		}

		long cityId = cityService.createCity(newAddressDTO.getCity(), newAddressDTO.getPostalAbbreviation());

		long districtId = districtService.createDistrict(newAddressDTO.getDistrict(), cityId);

		long addressTypeId = addressTypeService.findAddressTypeIdByName(newAddressDTO.getAddressType());

		Address newAddress = new AddressBuilder().postalCode(newAddressDTO.getPostalCode())
				.address(newAddressDTO.getAddress()).latitude(newAddressDTO.getLatitude())
				.longitude(newAddressDTO.getLongitude()).addressTypeId(addressTypeId).districtId(districtId).build();

		addressRepository.save(newAddress);

	}

	@Override
	public FullAddressDTO findAddressByPostalCode(Integer postalCode) {

		FullAddressDTO fullAddressDTO = null;
		if (addressRepository.existsById(postalCode)) {

			Address address = addressRepository.findFullAddress(postalCode);
			fullAddressDTO = new FullAddressDTOBuilder().postalCode(postalCode)
					.addressType(address.getAddressType().getName()).address(address.getAddress())
					.district(address.getDistrict().getName()).city(address.getDistrict().getCity().getName())
					.postalAbbreviation(address.getDistrict().getCity().getState().getPostalAbbreviation())
					.latitude(address.getLatitude()).longitude(address.getLongitude()).build();

		} else {

			CepDTO newAddress = cepApiService.findNewAddress(postalCode);
			LocationDTO locationDTO = googleApiService.findGeoCode(newAddress);

			fullAddressDTO = new FullAddressDTOBuilder().postalCode(postalCode).addressType(newAddress.getAddressType())
					.address(newAddress.getAddress()).district(newAddress.getDistrict()).city(newAddress.getCity())
					.postalAbbreviation(newAddress.getPostalAbbreviation()).latitude(locationDTO.getLatitude())
					.longitude(locationDTO.getLongitude()).build();

		}

		return fullAddressDTO;
	}

}
