package br.com.marcopc.contacorrente.controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.marcopc.contacorrente.entities.Conta;
import br.com.marcopc.contacorrente.entities.Cpf;
import br.com.marcopc.contacorrente.entities.User;
import br.com.marcopc.contacorrente.repositories.ContaRepository;
import br.com.marcopc.contacorrente.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContaRepository contaRepository;

	@GetMapping
	public List<User> findAll() {
		List<User> result = userRepository.findAll();
		return result;
	}

	@GetMapping(value = "/{id}")
	public User findById(@PathVariable Long id) {
		User result = userRepository.findById(id).get();
		return result;
	}
	
	@PostMapping
	public User insert(@RequestBody User user) {
		User result = userRepository.save(user);
		return result;
	}
	
	@PostMapping("/transferencia")
	public String realizarTransferencia(@RequestParam(value = "_contaOrigemId", required = true) Long contaOrigemId,
			@RequestParam(value = "_contaDestinoId", required = true) Long contaDestinoId,
			@RequestParam(value = "_valor", required = true) String valor) {

		try {

			Float valorTransferencia = Float.parseFloat(valor);

			Conta contaOrigem = contaRepository.findById(contaOrigemId).orElse(null);
			Conta contaDestino = contaRepository.findById(contaDestinoId).orElse(null);

			if (contaOrigem == null || contaDestino == null) {
				return "Contas não encontradas";
			}

			if (valorTransferencia <= 0) {
				return "O valor da transferência deve ser positivo";
			}

			if (valorTransferencia > contaOrigem.getSaldo()) {
				return "Saldo insuficiente para realizar a transferência";
			}

			// Realiza a transferência
			contaOrigem.setSaldo(contaOrigem.getSaldo() - valorTransferencia);
			contaDestino.setSaldo(contaDestino.getSaldo() + valorTransferencia);

			// Atualiza as contas no banco de dados
			contaRepository.save(contaOrigem);
			contaRepository.save(contaDestino);

		} catch (MethodArgumentTypeMismatchException e) {
			return "Erro na chamada da API: Verifique os tipos dos parâmetros";

		}
		return "Transferência realizada com sucesso";
	}

		

	@PostMapping("/cadastrarUsuario")
	public String cadastrarUsuario(@RequestParam String nome, @RequestParam String senha, @RequestParam String endereco,
			@RequestParam Long cpf) {

		try {
			// Verifica se o CPF já está cadastrado
			if (userRepository.existsById(cpf)) {
				return "CPF já cadastrado. Escolha outro CPF.";
			}

			// Cria um novo usuário
			User novoUsuario = null;
			novoUsuario = new User(novoUsuario);
			novoUsuario.setNome(nome);
			novoUsuario.setSenha(criptografarSenha(senha));
			novoUsuario.setEndereco(endereco);

			// Cria um novo CPF e associa ao usuário
			Cpf novoCpf = new Cpf();
			novoCpf.setCpf(cpf);
			novoUsuario.setCpf(novoCpf);

			// Salva o novo usuário no banco de dados
			userRepository.save(novoUsuario);

			return "Usuário cadastrado com sucesso!";
		} catch (Exception e) {
			return "Erro ao cadastrar usuário: " + e.getMessage();
		}
	}

	// Método para criptografar a senha
	private String criptografarSenha(String senha) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(senha.getBytes("UTF-8"));
			return new BigInteger(1, messageDigest.digest()).toString(16);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Erro ao criptografar a senha", e);
		}
	}
}