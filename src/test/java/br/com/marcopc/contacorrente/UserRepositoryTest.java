package br.com.marcopc.contacorrente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.com.marcopc.contacorrente.controllers.UserController;
import br.com.marcopc.contacorrente.entities.Client;
import br.com.marcopc.contacorrente.entities.Count;
import br.com.marcopc.contacorrente.repositories.ClientRepository;
import br.com.marcopc.contacorrente.repositories.CountRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserController userController;

	@MockBean
	private ClientRepository clientRepository;

	@MockBean
	private CountRepository countRepository;

	@Test
	void testCadastrarUsuario() {
		when(clientRepository.save(any())).thenReturn(new Client());

		String result = userController.cadastrarUsuario("Joao", "Endereco", "123456789");

		assertEquals("Usuário cadastrado com sucesso!", result);
	}

	@Test
	void testAbrirConta() {
		when(clientRepository.findById(anyString())).thenReturn(Optional.of(new Client()));

		String result = userController.abrirConta("1", "1234", "123456", "100.0", "jkljdafsiuiowpqjeiorj");

		assertEquals("Conta aberta com sucesso!", result);
	}
	
    @Test
    void testTransferencia() {
    	when(clientRepository.findById(anyString())).thenReturn(Optional.of(new Client()));
        when(clientRepository.save(any())).thenReturn(new Count());

        String result = userController.realizarTransferencia("1", "2", "50");

        assertEquals("Transferência realizada com sucesso!", result);
    }

    @Test
    void testBuscarPorId() {
    	when(clientRepository.findById(anyString())).thenReturn(Optional.of(new Client()));

        Client response = userController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.getClient_id());
    }


}
