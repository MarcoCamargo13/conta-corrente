package br.com.marcopc.contacorrente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marcopc.contacorrente.entities.Client;
import br.com.marcopc.contacorrente.entities.Count;

public interface ClientRepository extends JpaRepository<Client, String> {


}
