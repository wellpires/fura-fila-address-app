package br.com.furafila.addressapp.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import br.com.furafila.addressapp.model.District;
import br.com.furafila.addressapp.repository.DistrictRepository;
import br.com.furafila.addressapp.service.DistrictService;
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class DistrictServiceImplTest {

	@InjectMocks
	private DistrictService districtService = new DistrictServiceImpl();

	@Mock
	private DistrictRepository districtRepository;

	@Test
	public void shouldCreateDistrictWithSuccess() {

		District district = null;
		when(districtRepository.findByNameAndCityId(anyString(), anyLong())).thenReturn(district);

		District districtSaved = new District();
		districtSaved.setId(30l);
		when(districtRepository.save(any())).thenReturn(districtSaved);

		long districtId = districtService.createDistrict("Alphaville", 20);

		assertThat(districtSaved.getId(), equalTo(districtId));

	}

	@Test
	public void shouldNotCreateDistrictAndReturnIdFoundInDatabase() {

		District district = new District();
		district.setId(30l);
		when(districtRepository.findByNameAndCityId(anyString(), anyLong())).thenReturn(district);

		long districtId = districtService.createDistrict("Alphaville", 20);

		verify(districtRepository, never()).save(any());

		assertThat(district.getId(), equalTo(districtId));

	}

}
