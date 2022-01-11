package br.com.furafila.addressapp.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import br.com.furafila.addressapp.dto.FullAddressDTO;
import br.com.furafila.addressapp.service.AddressService;

@ContextConfiguration
public class AddressServiceImplTest {

	@Autowired
	private AddressService addressService;

	@Test
	public void shouldReturnFullAddressWithPostalCodeCorrect() {

		FullAddressDTO fullAddressDTO = addressService.findAddressByPostalCode(06333450);
		
		
	}

	@Configuration
	static class Config {

		@Bean
		public AddressService addressService() {
			return new AddressServiceImpl();
		}

	}

}
