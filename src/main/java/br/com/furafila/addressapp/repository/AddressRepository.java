package br.com.furafila.addressapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.furafila.addressapp.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Address findByPostalCodeAndAddressIgnoreCase(Integer postalCode, String address);

}
