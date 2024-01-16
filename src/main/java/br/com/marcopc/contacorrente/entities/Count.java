package br.com.marcopc.contacorrente.entities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_count")
public class Count {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long count_id;
	
	@Column(nullable = true)
	private String agencia;
	
	@Column(nullable = true, unique = true)
	private String num_conta;

	@Column(nullable = true)
	private Float saldo;
	
	@Column(nullable = true)
	private String password; 
	
	@Column(nullable = false)
    private Long client_id;
        
	public Count() {

	}

	public String getNum_conta() {
	    return num_conta;
	}

	public void setNum_conta(String num_conta) {
	    this.num_conta = num_conta;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Long getClient() {
		return client_id;
	}

	public void setClient(Long client_id) {
		this.client_id = client_id;
	}

	public Float getSaldo() {
		return saldo;
	}
	
	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}

	public Long getCountId() {
		return count_id;
	}

	public void setCountId(Long count_id) {
		this.count_id = count_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(senha.getBytes("UTF-8"));
		this.password = new BigInteger(1, messageDigest.digest()).toString(16);
	}

}