package br.com.marcopc.contacorrente.entities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@Column(nullable = false)
	private String senha;// Senha criptografada

	private String endereco;


    @ManyToOne
    @JoinColumn(name = "cpf") // Ajustado para "cpfId"
    private Cpf cpf;

    @OneToMany(mappedBy = "userId")
    private List<Conta> contas;

	public User(User dado) {
		this.nome = dado.getNome();
		this.senha = dado.getSenha();
		this.endereco = dado.getEndereco();
		this.cpf = dado.getCpf();
	}
	
	public User() {
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(senha.getBytes("UTF-8"));
		this.senha = new BigInteger(1, messageDigest.digest()).toString(16);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cpf getCpf() {
		return cpf;
	}

	public void setCpf(Cpf cpf) {
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
	
}
