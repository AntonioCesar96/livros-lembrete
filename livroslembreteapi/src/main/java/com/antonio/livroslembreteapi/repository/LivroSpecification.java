package com.antonio.livroslembreteapi.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.antonio.livroslembreteapi.models.Livro;
import com.antonio.livroslembreteapi.models.Usuario;

public class LivroSpecification {

	public static Specification<Livro> filtrar(Long usuario) {
		return new Specification<Livro>() {

			@Override
			public Predicate toPredicate(Root<Livro> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

				List<Predicate> conditions = new ArrayList<Predicate>();
				if (usuario != null) {
					Predicate equal = cb.equal(root.<Usuario>get("usuario").<Long>get("id"), usuario);
					conditions.add(equal);
				}

				return cb.and(conditions.toArray(new Predicate[0]));
			}
		};
	}
}
