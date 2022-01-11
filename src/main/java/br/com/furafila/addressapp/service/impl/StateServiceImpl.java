package br.com.furafila.addressapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.furafila.addressapp.exception.StateNotFoundException;
import br.com.furafila.addressapp.model.State;
import br.com.furafila.addressapp.repository.StateRepository;
import br.com.furafila.addressapp.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	private StateRepository stateRepository;

	@Override
	public long findIdByPostalAbbreviation(String postalAbbreviation) {

		State state = stateRepository.findIdByPostalAbbreviation(postalAbbreviation)
				.orElseThrow(StateNotFoundException::new);

		return state.getId();
	}

}
