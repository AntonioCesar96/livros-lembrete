package com.antonio.livroslembreteapi.services;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.antonio.livroslembreteapi.models.Livro;
import com.antonio.livroslembreteapi.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	public Page<Livro> findAll(Pageable pageable) {
		Page<Livro> all = livroRepository.findAll(pageable);
		return all;
	}

	public Response save(Livro livro) {
		livro = livroRepository.save(livro);
		return Response.ok(livro).build();
	}

	public Response findOne(Long id) {
		Livro livro = livroRepository.findOne(id);
		return Response.ok(livro).build();
	}

	public Response update(Long id, Livro livro) {
		livro = livroRepository.save(livro);
		return Response.ok(livro).build();
	}

	public Response delete(Long id) {
		livroRepository.delete(id);
		return Response.ok().build();
	}
}
