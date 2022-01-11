package br.com.furafila.addressapp.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.furafila.addressapp.model.City;
import br.com.furafila.addressapp.model.District;
import br.com.furafila.addressapp.repository.DistrictRepository;
import br.com.furafila.addressapp.service.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictRepository districtRepository;

	@Override
	public long createDistrict(String district, long cityId) {

		District discrictFound = districtRepository.findByNameAndCityId(district, cityId);

		if (Objects.nonNull(discrictFound)) {
			return discrictFound.getId();
		}

		District newDistrict = new District();
		newDistrict.setName(district);

		City newCity = new City();
		newCity.setId(cityId);

		newDistrict.setCity(newCity);

		District districtSaved = districtRepository.save(newDistrict);

		return districtSaved.getId();
	}

}
