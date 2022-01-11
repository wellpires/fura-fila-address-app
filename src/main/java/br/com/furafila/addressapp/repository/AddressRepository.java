package br.com.furafila.addressapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.furafila.addressapp.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	Address findByPostalCodeAndAddressIgnoreCase(Integer postalCode, String address);

	@Query("SELECT a, at, d, c, s FROM Address a INNER JOIN a.addressType at INNER JOIN a.district d INNER JOIN d.city c INNER JOIN c.state s where a.postalCode = :postalCode")
	Address findFullAddress(@Param("postalCode") Integer postalCode);

}
