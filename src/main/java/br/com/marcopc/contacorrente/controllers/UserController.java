package br.com.marcopc.contacorrente.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.marcopc.contacorrente.controllers.exception.ServiceException;
import br.com.marcopc.contacorrente.entities.Client;
import br.com.marcopc.contacorrente.entities.Count;
import br.com.marcopc.contacorrente.repositories.ClientRepository;
import br.com.marcopc.contacorrente.repositories.CountRepository;



@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CountRepository countRepository;

	@GetMapping
	public List<Client> findAll() {
		List<Client> result = clientRepository.findAll();
		return result;
	}
	
	@GetMapping(value = "/{id}")
	public Client findById(@PathVariable String id) {
		Client result = clientRepository.findById(id).get();
		return result;
	}

	@PostMapping(value = "/{insert}")
	public ResponseEntity<Client> insert(@RequestBody Client obj) {
		try {
		obj = clientRepository.save(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getClient_id()).toUri();
		return ResponseEntity.created(uri).body(obj);
		}catch (ServiceException e) {
			return ResponseEntity.unprocessableEntity().build();
			
		}
	}
	
	@PostMapping("/transferencia")
	public String realizarTransferencia(@RequestParam(value = "_contaOrigem", required = true) String contaOrigem,
			@RequestParam(value = "_contaDestino", required = true) String contaDestino,
			@RequestParam(value = "_valor", required = true) String valor) {
		
		String contaOrigemAux = null;
		String contaDestinoAux = null; 
		Count contaOrigemAtual = null;
		Count contaDestinoAtual = null;
		try {
			// Busca a conta de origem
			contaOrigemAtual = countRepository.findByNumConta(contaOrigem);
			contaDestinoAtual = countRepository.findByNumConta(contaDestino);

			contaOrigemAux = contaOrigemAtual.getSaldo().toString();
			contaDestinoAux = contaDestinoAtual.getSaldo().toString();
			
	        if (contaOrigemAtual == null) {
	            return "Conta de origem não encontrada";
	        }

			Float valorTransferencia = Float.parseFloat(valor);
			
			if (contaOrigemAtual == null || contaDestinoAtual == null) {
				return "Contas não encontradas!!";
			}

			if (valorTransferencia <= 0) {
				return "O valor da transferência deve ser positivo";
			}

			if (valorTransferencia > contaOrigemAtual.getSaldo()) {
				return "Saldo insuficiente para realizar a transferência";
			}

			
			// Realiza a transferência
			contaOrigemAtual.setSaldo(contaOrigemAtual.getSaldo() - valorTransferencia);
			contaDestinoAtual.setSaldo(contaDestinoAtual.getSaldo() + valorTransferencia);

			// Atualiza as contas no banco de dados
			countRepository.save(contaOrigemAtual);
			countRepository.save(contaDestinoAtual);

		} catch (MethodArgumentTypeMismatchException e) {
			return "Erro na chamada da API transferencia: Verifique os tipos dos parâmetros";

		}
		return "Transferencia de R$ " + valor  +"\nSaldo Atual\nConta Origem  : " + contaOrigemAux+ "\nConta Destino : " + contaDestinoAux
				+ "\n\nTransferência realizada com sucesso" + "\nConta Origem  : " +  contaOrigemAtual.getSaldo() + "\nConta Destino : " + contaDestinoAtual.getSaldo();
		
		
	}

	@PostMapping("/cadastrarUsuario")
	public String cadastrarUsuario(
	        @RequestParam(value = "_name", required = true) String name,
	        @RequestParam(value = "_address", required = true) String address,
	        @RequestParam(value = "_document", required = true) String document) {
	    try {
	        // Verifica se o CPF já está cadastrado
	        if (clientRepository.existsById(document)) {
	            return "CPF já cadastrado. Escolha outro CPF.";
	        }

	        // Cria um novo usuário
	        Client newUsuario = new Client();
	        newUsuario.setName(name);
	        newUsuario.setAddress(address);
	        newUsuario.setDocument(document);
	        newUsuario.setStatus(Client.StatusConta.INATIVA); // Defina o status padrão, se necessário

	        // Salva o novo usuário no banco de dados
	        clientRepository.save(newUsuario);

	        return "Usuário cadastrado com sucesso!";
	    } catch (MethodArgumentTypeMismatchException e) {
	        return "Erro ao cadastrar o usuário.";
	    }
	}

    @PostMapping("/abrirConta")
    public String abrirConta(
    		@RequestParam(value = "_clientId", required = true) String clientId,
    		@RequestParam(value = "_agencia", required = true) String agencia,
    		@RequestParam(value = "_num_conta", required = true) String num_conta,
    		@RequestParam(value = "_saldoInicial", required = true) String saldoInicial,
    		@RequestParam(value = "_senha", required = true) String senha) {
        try {
            // Verifica se o cliente existe
            Client cliente = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

            // Restante do código permanece o mesmo
            // Cria uma nova conta
            Count novaConta = new Count();
            novaConta.setAgencia(agencia);
            novaConta.setSaldo(Float.parseFloat(saldoInicial));
            novaConta.setClient(Long.parseLong(clientId));
            novaConta.setNum_conta(num_conta);
            try {
				novaConta.setPassword(senha);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	        // Alterar status usuário
	        cliente.setStatus(Client.StatusConta.ATIVA); // Defina o status padrão, se necessário
	        
	        // Salva status no banco de dados
	        clientRepository.save(cliente);

            // Salva a nova conta no banco de dados
            countRepository.save(novaConta);
            
            return "Conta aberta com sucesso para o cliente " + cliente.getName() + "!";
        } catch (MethodArgumentTypeMismatchException e) {
            return "Erro ao abrir a conta para o cliente.";
        }
    }

    @GetMapping("/todos")
    public List<Client> listarUsuarios() {
        return clientRepository.findAll();
    }
	
}