package br.com.marcopc.contacorrente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marcopc.contacorrente.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
