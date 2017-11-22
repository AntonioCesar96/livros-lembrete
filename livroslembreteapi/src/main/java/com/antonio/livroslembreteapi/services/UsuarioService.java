package com.antonio.livroslembreteapi.services;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import com.antonio.livroslembreteapi.models.Usuario;
import com.antonio.livroslembreteapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;

	public Page<Usuario> findAll(Pageable pageable) {
		Page<Usuario> all = usuarioRepository.findAll(pageable);
		return all;
	}

	public Response logar(String email, String senha) {
		senha = md5PasswordEncoder.encodePassword(senha, null);
		Usuario usuario = usuarioRepository.logar(email, senha);
		return Response.ok(usuario).build();
	}

	public Response save(Usuario usuario) {
		usuario.setSenha(md5PasswordEncoder.encodePassword(usuario.getSenha(), null));
		usuario = usuarioRepository.save(usuario);
		return Response.ok(usuario).build();
	}

	public Response findOne(Long id) {
		Usuario usuario = usuarioRepository.findOne(id);
		return Response.ok(usuario).build();
	}

	public Response update(Long id, Usuario usuario) {
		usuario.setSenha(md5PasswordEncoder.encodePassword(usuario.getSenha(), null));
		usuario = usuarioRepository.save(usuario);
		return Response.ok(usuario).build();
	}

	public Response delete(Long id) {
		usuarioRepository.delete(id);
		return Response.ok().build();
	}
}
