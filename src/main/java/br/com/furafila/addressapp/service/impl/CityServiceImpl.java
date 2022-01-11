package br.com.furafila.addressapp.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.furafila.addressapp.model.City;
import br.com.furafila.addressapp.model.State;
import br.com.furafila.addressapp.repository.CityRepository;
import br.com.furafila.addressapp.service.CityService;
import br.com.furafila.addressapp.service.StateService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateService stateService;

	@Override
	public long createCity(String cityName, String postalAbbreviation) {

		long stateId = stateService.findIdByPostalAbbreviation(postalAbbreviation);

		City cityFounded = cityRepository.findByNameAndPostalAbbreviation(cityName, postalAbbreviation);

		if (Objects.nonNull(cityFounded)) {
			return cityFounded.getId();
		}

		City city = new City();
		city.setName(cityName);

		State state = new State();
		state.setId(stateId);
		city.setState(state);

		City citySaved = cityRepository.save(city);

		return citySaved.getId();
	}

}
