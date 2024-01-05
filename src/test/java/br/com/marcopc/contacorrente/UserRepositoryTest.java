package br.com.marcopc.contacorrente;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.marcopc.contacorrente.entities.User;
import br.com.marcopc.contacorrente.repositories.UserRepository;
import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("Return Sucess")
	void findUserIdCase1() {
	
		User dado = new User();
		dado.setId((long) 1);
		dado.setNome("Teste");
		dado.setEndereco("Rua teste");
		try {
			dado.setSenha("123456");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.createUser(dado);
		
		Long id = (long) 1;
		Optional<User> returnUser =  this.userRepository.findById(id);
		
		returnUser.isEmpty();
	}
	
	private User createUser(User dado) {
		User newUser = new User(dado); 
		this.entityManager.persist(newUser);
		return newUser;
	}
	
}
