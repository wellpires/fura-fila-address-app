package br.com.furafila.addressapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.furafila.addressapp.model.State;

public interface StateRepository extends JpaRepository<State, Long> {

	Optional<State> findIdByPostalAbbreviation(String postalAbbreviation);

}
