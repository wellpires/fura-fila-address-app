package br.com.furafila.addressapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.furafila.addressapp.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

	@Query("select c from City c inner join c.state s where c.name = :cityName and s.postalAbbreviation = :postalAbbreviation")
	City findByNameAndPostalAbbreviation(@Param("cityName") String cityName,
			@Param("postalAbbreviation") String postalAbbreviation);

}
