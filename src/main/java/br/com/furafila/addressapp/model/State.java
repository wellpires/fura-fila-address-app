package br.com.furafila.addressapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UF")
public class State implements Serializable {

	private static final long serialVersionUID = 30136494578281535L;

	@Id
	@Column(name = "id_uf", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sigla_uf")
	private String postalAbbreviation;

	@Column(name = "desc_uf")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostalAbbreviation() {
		return postalAbbreviation;
	}

	public void setPostalAbbreviation(String postalAbbreviation) {
		this.postalAbbreviation = postalAbbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
