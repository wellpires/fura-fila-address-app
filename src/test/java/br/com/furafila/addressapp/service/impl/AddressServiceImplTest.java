package br.com.furafila.addressapp.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.furafila.addressapp.dto.CepDTO;
import br.com.furafila.addressapp.dto.FullAddressDTO;
import br.com.furafila.addressapp.dto.LocationDTO;
import br.com.furafila.addressapp.dto.NewAddressDTO;
import br.com.furafila.addressapp.model.Address;
import br.com.furafila.addressapp.model.AddressType;
import br.com.furafila.addressapp.model.City;
import br.com.furafila.addressapp.model.District;
import br.com.furafila.addressapp.model.State;
import br.com.furafila.addressapp.repository.AddressRepository;
import br.com.furafila.addressapp.service.AddressService;
import br.com.furafila.addressapp.service.AddressTypeService;
import br.com.furafila.addressapp.service.CepApiService;
import br.com.furafila.addressapp.service.CityService;
import br.com.furafila.addressapp.service.DistrictService;
import br.com.furafila.addressapp.service.GoogleApiService;
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class AddressServiceImplTest {

	@InjectMocks
	private AddressService addressService = new AddressServiceImpl();

	@Mock
	private CityService cityService;

	@Mock
	private DistrictService districtService;

	@Mock
	private AddressTypeService addressTypeService;

	@Mock
	private AddressRepository addressRepository;

	@Mock
	private CepApiService cepApiService;

	@Mock
	private GoogleApiService googleApiService;

	@Test
	public void shouldSaveAddressWhenItsNotFoundInDatabase()
			throws StreamReadException, DatabindException, IOException {

		NewAddressDTO newAddressDTO = new ObjectMapper()
				.readValue(Paths.get("src", "test", "resources", "NewAddressDTO.json").toFile(), NewAddressDTO.class);

		long addressTypeId = 1l;
		long districtId = 1l;
		long cityId = 1l;

		when(cityService.createCity(anyString(), anyString())).thenReturn(cityId);
		when(districtService.createDistrict(anyString(), anyLong())).thenReturn(districtId);
		when(addressTypeService.findAddressTypeIdByName(anyString())).thenReturn(addressTypeId);

		Address address = null;
		when(addressRepository.findByPostalCodeAndAddressIgnoreCase(anyInt(), anyString())).thenReturn(address);

		addressService.save(newAddressDTO);

		ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
		verify(addressRepository).save(captor.capture());

		Address addressCapt = captor.getValue();

		assertThat(newAddressDTO.getPostalCode(), equalTo(addressCapt.getPostalCode()));
		assertThat(newAddressDTO.getAddress(), equalTo(addressCapt.getAddress()));
		assertThat(newAddressDTO.getLatitude(), equalTo(addressCapt.getLatitude()));
		assertThat(newAddressDTO.getLongitude(), equalTo(addressCapt.getLongitude()));
		assertThat(addressTypeId, equalTo(addressCapt.getAddressType().getId()));
		assertThat(districtId, equalTo(addressCapt.getDistrict().getId()));

	}

	@Test
	public void shouldNotSaveAddressWhenItsFoundInDatabase()
			throws StreamReadException, DatabindException, IOException {

		Address address = new Address();
		when(addressRepository.findByPostalCodeAndAddressIgnoreCase(anyInt(), anyString())).thenReturn(address);

		NewAddressDTO newAddressDTO = new ObjectMapper()
				.readValue(Paths.get("src", "test", "resources", "NewAddressDTO.json").toFile(), NewAddressDTO.class);
		addressService.save(newAddressDTO);

		verify(addressRepository, never()).save(Mockito.any());

	}

	@Test
	public void shouldFindFullAddressInDatabase() {

		when(addressRepository.existsById(anyInt())).thenReturn(Boolean.TRUE);

		Address address = new Address();
		address.setAddress("logradouro teste");
		address.setLatitude(-69.18158181);
		address.setLongitude(-23.9889984);

		AddressType addressType = new AddressType();
		addressType.setName("Rua");
		address.setAddressType(addressType);

		District district = new District();
		district.setName("São Paulo");
		address.setDistrict(district);

		City city = new City();
		city.setName("São Paulo");
		address.getDistrict().setCity(city);

		State state = new State();
		state.setPostalAbbreviation("SP");
		address.getDistrict().getCity().setState(state);

		when(addressRepository.findFullAddress(anyInt())).thenReturn(address);

		int postalCode = 25666352;
		FullAddressDTO fullAddressDTO = addressService.findAddressByPostalCode(postalCode);

		assertThat(fullAddressDTO.getPostalCode(), equalTo(postalCode));
		assertThat(fullAddressDTO.getAddressType(), equalTo(address.getAddressType().getName()));
		assertThat(fullAddressDTO.getAddress(), equalTo(address.getAddress()));
		assertThat(fullAddressDTO.getDistrict(), equalTo(address.getDistrict().getName()));
		assertThat(fullAddressDTO.getCity(), equalTo(address.getDistrict().getCity().getName()));
		assertThat(fullAddressDTO.getPostalAbbreviation(),
				equalTo(address.getDistrict().getCity().getState().getPostalAbbreviation()));
		assertThat(fullAddressDTO.getLatitude(), equalTo(address.getLatitude()));
		assertThat(fullAddressDTO.getLongitude(), equalTo(address.getLongitude()));

	}

	@Test
	public void shouldNotFindFullAddressinDatabaseAndFindInAPI() {

		when(addressRepository.existsById(anyInt())).thenReturn(Boolean.FALSE);

		CepDTO cepDTO = new CepDTO();
		cepDTO.setAddressType("Rua");
		cepDTO.setAddress("teste da silva");
		cepDTO.setDistrict("São Paulo");
		cepDTO.setCity("São Paulo");
		cepDTO.setPostalAbbreviation("SP");
		when(cepApiService.findNewAddress(anyInt())).thenReturn(cepDTO);

		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setLatitude(-65.651151);
		locationDTO.setLongitude(-15.651151);
		when(googleApiService.findGeoCode(any())).thenReturn(locationDTO);

		int postalCode = 25666352;
		FullAddressDTO fullAddressDTO = addressService.findAddressByPostalCode(postalCode);

		assertThat(fullAddressDTO.getPostalCode(), equalTo(postalCode));
		assertThat(fullAddressDTO.getAddressType(), equalTo(cepDTO.getAddressType()));
		assertThat(fullAddressDTO.getAddress(), equalTo(cepDTO.getAddress()));
		assertThat(fullAddressDTO.getDistrict(), equalTo(cepDTO.getDistrict()));
		assertThat(fullAddressDTO.getCity(), equalTo(cepDTO.getCity()));
		assertThat(fullAddressDTO.getPostalAbbreviation(), equalTo(cepDTO.getPostalAbbreviation()));
		assertThat(fullAddressDTO.getLongitude(), equalTo(locationDTO.getLongitude()));
		assertThat(fullAddressDTO.getLatitude(), equalTo(locationDTO.getLatitude()));

	}

}
