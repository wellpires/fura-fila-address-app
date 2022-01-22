package br.com.furafila.addressapp.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.furafila.addressapp.exception.StateNotFoundException;
import br.com.furafila.addressapp.model.State;
import br.com.furafila.addressapp.repository.StateRepository;
import br.com.furafila.addressapp.service.StateService;
import br.com.furafila.addressapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class StateServiceImplTest {

	@InjectMocks
	private StateService stateService = new StateServiceImpl();

	@Mock
	private StateRepository stateRepository;

	@Test
	public void shouldFindIdByPostalAbbreviation() {

		State state = new State();
		long id = 123l;
		state.setId(id);
		when(stateRepository.findIdByPostalAbbreviation(anyString())).thenReturn(Optional.ofNullable(state));

		long stateId = this.stateService.findIdByPostalAbbreviation("06333450");

		MatcherAssert.assertThat(stateId, equalTo(id));

	}

	@Test
	public void shouldNotFindIdByPostalAbbreviation() {

		State state = null;
		when(stateRepository.findIdByPostalAbbreviation(anyString())).thenReturn(Optional.ofNullable(state));

		assertThrows(StateNotFoundException.class, () -> {
			this.stateService.findIdByPostalAbbreviation("06333450");
		});

	}

}
