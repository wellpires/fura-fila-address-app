package br.com.furafila.addressapp.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.furafila.addressapp.dto.FullAddressDTO;
import br.com.furafila.addressapp.dto.NewAddressDTO;
import br.com.furafila.addressapp.exception.AddressTypeNotFoundException;
import br.com.furafila.addressapp.exception.ServerErrorApiException;
import br.com.furafila.addressapp.exception.StateNotFoundException;
import br.com.furafila.addressapp.request.NewAddressRequest;
import br.com.furafila.addressapp.response.ErrorResponse;
import br.com.furafila.addressapp.response.FullAddressResponse;
import br.com.furafila.addressapp.service.AddressService;
import br.com.furafila.addressapp.util.Messages;
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
class AddressControllerTest {

	private static final String ADDRESS_PATH = "/address";
	private static final String FIND_ADDRESS_PATH = ADDRESS_PATH.concat("/{postalCode}");

	@MockBean
	private AddressService addressService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private NewAddressDTO newAddressDTO;

	@BeforeEach
	public void setup() throws StreamReadException, DatabindException, IOException {
		newAddressDTO = mapper.readValue(Paths.get("src", "test", "resources", "NewAddressDTO.json").toFile(),
				NewAddressDTO.class);
	}

	@Test
	void shouldSaveAddressWithSuccess() throws Exception {

		this.mockMvc
				.perform(post(ADDRESS_PATH).contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(new NewAddressRequest(newAddressDTO))))
				.andExpect(status().isNoContent()).andDo(print()).andReturn();

	}

	@Test
	void shouldNotSaveAddressBecauseAddressInfoIsNull() throws Exception {

		testAddressFieldsValidation(new NewAddressRequest(), Messages.ADDRESS_INFO_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecauseAddressFieldIsBlank() throws Exception {

		newAddressDTO.setAddress("");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.ADDRESS_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecauseAddressFieldIsNotValid() throws Exception {

		newAddressDTO.setAddress("a");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.ADDRESS_LENGTH_IS_NOT_VALID);

	}

	@Test
	void shouldNotSaveAddressBecauseDistrictFieldIsBlank() throws Exception {

		newAddressDTO.setDistrict("");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.DISTRICT_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecauseDistrictFieldIsNotValid() throws Exception {

		newAddressDTO.setDistrict("aajdhvaosduyavsdouaysvdaousdyvaosduyavsoduaysvdoaus");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.DISTRICT_LENGTH_IS_NOT_VALID);

	}

	@Test
	void shouldNotSaveAddressBecausePostalAbbreviationFieldIsBlank() throws Exception {

		newAddressDTO.setPostalAbbreviation("");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.POSTAL_ABBREVIATION_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecausePostalAbbreviationFieldIsNotValid() throws Exception {

		newAddressDTO.setPostalAbbreviation("ASD");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.POSTAL_ABBREVIATION_IS_NOT_VALID);

	}

	@Test
	void shouldNotSaveAddressBecausePostalCodeFieldIsNull() throws Exception {

		newAddressDTO.setPostalCode(null);

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.POSTALCODE_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecausePostalCodeFieldIsNotValid() throws Exception {

		newAddressDTO.setPostalCode(1234567899);

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.POSTALCODE_IS_NOT_VALID);

	}

	@Test
	void shouldNotSaveAddressBecauseCityFieldIsNull() throws Exception {

		newAddressDTO.setCity(null);

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.CITY_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecauseCityFieldIsNotValid() throws Exception {

		newAddressDTO.setCity("qwertyuiopçlkjhgfdsazxcvbnmlopi");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.CITY_LENGTH_IS_NOT_VALID);

	}

	@Test
	void shouldNotSaveAddressBecauseAddressTypeFieldIsNull() throws Exception {

		newAddressDTO.setAddressType(null);

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.ADDRESS_TYPE_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecauseAddressTypeFieldIsNotValid() throws Exception {

		newAddressDTO.setAddressType("aa");

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.ADDRESS_TYPE_LENGTH_IS_NOT_VALID);

	}

	@Test
	void shouldNotSaveAddressBecauseLatitudeFieldIsNull() throws Exception {

		newAddressDTO.setLatitude(null);

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.LATITUDE_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecauseLongitudeFieldIsNull() throws Exception {

		newAddressDTO.setLongitude(null);

		testAddressFieldsValidation(new NewAddressRequest(newAddressDTO), Messages.LONGITUDE_IS_REQUIRED);

	}

	@Test
	void shouldNotSaveAddressBecauseAddressTypeNotFoundExceptionThrown() throws JsonProcessingException, Exception {

		doThrow(new AddressTypeNotFoundException()).when(addressService).save(any());

		mockMvc.perform(post(ADDRESS_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new NewAddressRequest(newAddressDTO))))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();

	}

	@Test
	void shouldNotSaveAddressBecauseStateNotFoundExceptionhrown() throws JsonProcessingException, Exception {

		doThrow(new StateNotFoundException()).when(addressService).save(any());

		mockMvc.perform(post(ADDRESS_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new NewAddressRequest(newAddressDTO))))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();

	}

	private void testAddressFieldsValidation(NewAddressRequest newAddressRequest, String comparisonMessage)
			throws Exception {

		MvcResult result = mockMvc
				.perform(post(ADDRESS_PATH).contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(newAddressRequest)))
				.andExpect(status().isBadRequest()).andDo(print()).andReturn();

		ErrorResponse errorResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

		assertThat(errorResponse.getMessage(), equalTo(comparisonMessage));

	}

	@Test
	void shouldFindAddressWithSuccess() throws JsonProcessingException, Exception {

		FullAddressDTO fullAddressDTO = new FullAddressDTO();
		fullAddressDTO.setAddress("address");
		fullAddressDTO.setAddressType("Rua");
		fullAddressDTO.setCity("city");
		fullAddressDTO.setDistrict("district");
		fullAddressDTO.setLatitude(12.654);
		fullAddressDTO.setLongitude(-65.6547);
		fullAddressDTO.setPostalAbbreviation("SP");
		fullAddressDTO.setPostalCode(65222358);
		when(addressService.findAddressByPostalCode(anyInt())).thenReturn(fullAddressDTO);

		Map<String, Object> params = new HashMap<>();
		params.put("postalCode", 22555852);
		URI uri = UriComponentsBuilder.fromPath(FIND_ADDRESS_PATH).buildAndExpand(params).toUri();

		MvcResult result = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();

		FullAddressResponse fullAddressResponse = mapper.readValue(result.getResponse().getContentAsString(),
				FullAddressResponse.class);

		assertNotNull(fullAddressResponse.getFullAddressDTO());
		assertThat(fullAddressResponse.getFullAddressDTO().getAddress(), equalTo(fullAddressDTO.getAddress()));
		assertThat(fullAddressResponse.getFullAddressDTO().getAddressType(), equalTo(fullAddressDTO.getAddressType()));
		assertThat(fullAddressResponse.getFullAddressDTO().getCity(), equalTo(fullAddressDTO.getCity()));
		assertThat(fullAddressResponse.getFullAddressDTO().getDistrict(), equalTo(fullAddressDTO.getDistrict()));
		assertThat(fullAddressResponse.getFullAddressDTO().getLatitude(), equalTo(fullAddressDTO.getLatitude()));
		assertThat(fullAddressResponse.getFullAddressDTO().getLongitude(), equalTo(fullAddressDTO.getLongitude()));
		assertThat(fullAddressResponse.getFullAddressDTO().getPostalAbbreviation(),
				equalTo(fullAddressDTO.getPostalAbbreviation()));
		assertThat(fullAddressResponse.getFullAddressDTO().getPostalCode(), equalTo(fullAddressDTO.getPostalCode()));

	}

	@Test
	void shouldNotFindAddressBecauseApiErrorWasThrown() throws JsonProcessingException, Exception {

		doThrow(new ServerErrorApiException("path/test", HttpStatus.BAD_GATEWAY)).when(addressService)
				.findAddressByPostalCode(anyInt());

		Map<String, Object> params = new HashMap<>();
		params.put("postalCode", 22555852);
		URI uri = UriComponentsBuilder.fromPath(FIND_ADDRESS_PATH).buildAndExpand(params).toUri();

		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
				.andDo(print());

	}

	@Test
	void shouldNotFindAddressBecauseGenericExceptionWasThrown() throws JsonProcessingException, Exception {

		doThrow(new RuntimeException("TESTE ERROR")).when(addressService).findAddressByPostalCode(anyInt());

		Map<String, Object> params = new HashMap<>();
		params.put("postalCode", 22555852);
		URI uri = UriComponentsBuilder.fromPath(FIND_ADDRESS_PATH).buildAndExpand(params).toUri();

		mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
				.andDo(print());
	}

}
