package br.com.marcopc.contacorrente.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cpf")
public class Cpf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cpfId;
	
	@Column(unique = true)
	private Long cpf;
	

	public Cpf() {

	}

	public Long getId() {
		return cpfId;
	}

	public void setId(Long cpfId) {
		this.cpfId = cpfId;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
}