package com.antonio.livroslembreteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.antonio.livroslembreteapi.models.Livro;

public interface LivroRepository
		extends CrudRepository<Livro, Long>, JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {

}
