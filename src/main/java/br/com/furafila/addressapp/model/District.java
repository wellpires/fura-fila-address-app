package br.com.furafila.addressapp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bairro")
public class District implements Serializable {

	private static final long serialVersionUID = -5974238117340312990L;

	@Id
	@Column(name = "id_bairro", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "desc_bairro")
	private String name;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_cidade_fk", columnDefinition = "int4")
	private City city;

	public District(Long districtId) {
		this.id = districtId;
	}

	public District() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
