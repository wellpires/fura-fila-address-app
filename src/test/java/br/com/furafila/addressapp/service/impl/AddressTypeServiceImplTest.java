package br.com.furafila.addressapp.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.furafila.addressapp.exception.AddressTypeNotFoundException;
import br.com.furafila.addressapp.model.AddressType;
import br.com.furafila.addressapp.repository.AddressTypeRepository;
import br.com.furafila.addressapp.service.AddressTypeService;
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class AddressTypeServiceImplTest {

	@InjectMocks
	private AddressTypeService addressTypeService = new AddressTypeServiceImpl();

	@Mock
	private AddressTypeRepository addressTypeRepository;

	@Test
	public void shouldFindAddressTypeIdByName() {

		AddressType addressType = new AddressType();
		long id = 105l;
		addressType.setId(id);
		when(addressTypeRepository.findIdByName(anyString())).thenReturn(Optional.ofNullable(addressType));

		long addressIdFound = addressTypeService.findAddressTypeIdByName("Rua");

		assertThat(addressIdFound, equalTo(id));

	}

	@Test
	public void shouldNotFindAddressTypeIdByName() {

		AddressType addressType = null;
		when(addressTypeRepository.findIdByName(anyString())).thenReturn(Optional.ofNullable(addressType));

		assertThrows(AddressTypeNotFoundException.class, () -> {
			addressTypeService.findAddressTypeIdByName("Rua");
		});

	}

}
