package br.com.marcopc.contacorrente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.marcopc.contacorrente.entities.Count;

public interface CountRepository extends JpaRepository<Count, String> {
	
    @Query("SELECT c FROM Count c WHERE c.num_conta = :numConta")
    Count findByNumConta(String numConta);
}