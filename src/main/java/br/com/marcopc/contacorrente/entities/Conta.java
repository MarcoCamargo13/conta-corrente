package br.com.marcopc.contacorrente.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_conta")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private Float saldo;
	private Boolean status;
	private String usuario_cpf;
	
	@Column(unique = true)
	private String agencia;
	
	@Column(unique = true)
	private String num_conta;

	@ManyToOne
	@JoinColumn(name = "user_cpf", referencedColumnName = "cpf") // Corrigido para "user_cpf" e referenciado a coluna "cpf" em User
	private User user;

	public String getNum_conta() {
		return num_conta;
	}

	public void setNum_conta(String num_conta) {
		this.num_conta = num_conta;
	}


	private Conta() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}


	public String getUsuario_cpf() {
		return usuario_cpf;
	}

	public void setUsuario_cpf(String usuario_cpf) {
		this.usuario_cpf = usuario_cpf;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}
