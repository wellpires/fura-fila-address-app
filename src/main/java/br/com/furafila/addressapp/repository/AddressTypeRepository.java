package br.com.furafila.addressapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.furafila.addressapp.model.AddressType;

public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {

	Optional<AddressType> findIdByName(String name);

}
