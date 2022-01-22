package br.com.furafila.addressapp.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.furafila.addressapp.model.City;
import br.com.furafila.addressapp.repository.CityRepository;
import br.com.furafila.addressapp.service.CityService;
import br.com.furafila.addressapp.service.StateService;
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class CityServiceImplTest {

	@InjectMocks
	private CityService cityService = new CityServiceImpl();

	@Mock
	private CityRepository cityRepository;

	@Mock
	private StateService stateService;

	@Test
	public void shouldCreateCityWithSucess() {

		City cityFound = null;
		when(cityRepository.findByNameAndPostalAbbreviation(anyString(), anyString())).thenReturn(cityFound);

		long stateId = 10l;
		when(stateService.findIdByPostalAbbreviation(anyString())).thenReturn(stateId);

		City citySaved = new City();
		citySaved.setId(20l);
		when(cityRepository.save(any())).thenReturn(citySaved);

		long cityIdSaved = cityService.createCity("São Paulo", "SP");

		assertThat(citySaved.getId(), equalTo(cityIdSaved));

	}

	@Test
	public void shouldNotCreateCityAndReturnIdFoundInDatabase() {

		City cityFound = new City();
		cityFound.setId(10l);
		when(cityRepository.findByNameAndPostalAbbreviation(anyString(), anyString())).thenReturn(cityFound);

		verify(cityRepository, never()).save(any());

		long cityIdFound = cityService.createCity("São Paulo", "SP");

		assertThat(cityFound.getId(), equalTo(cityIdFound));

	}

}
