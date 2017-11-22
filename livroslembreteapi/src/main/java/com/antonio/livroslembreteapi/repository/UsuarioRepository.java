package com.antonio.livroslembreteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.antonio.livroslembreteapi.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>, JpaRepository<Usuario, Long> {

	@Query(value = "SELECT u FROM Usuario u WHERE u.email = ?1 AND u.senha = ?2")
	Usuario logar(String email, String senha);
}
