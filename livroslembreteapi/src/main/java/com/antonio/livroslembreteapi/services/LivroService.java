package com.antonio.livroslembreteapi.services;

import java.util.List;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.antonio.livroslembreteapi.models.Livro;
import com.antonio.livroslembreteapi.repository.LivroRepository;
import com.antonio.livroslembreteapi.repository.LivroSpecification;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	public List<Livro> findAll(int page, int size, Long usuario) {
		Specification<Livro> specification = LivroSpecification.filtrar(usuario);
		Page<Livro> all = livroRepository.findAll(specification, new PageRequest(page, size));
		return all.getContent();
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
