package br.com.furafila.addressapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.furafila.addressapp.model.District;

public interface DistrictRepository extends JpaRepository<District, Long> {

	District findByNameAndCityId(String district, Long cityId);

}
