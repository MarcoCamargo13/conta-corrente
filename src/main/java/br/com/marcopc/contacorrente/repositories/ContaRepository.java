package br.com.marcopc.contacorrente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marcopc.contacorrente.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
