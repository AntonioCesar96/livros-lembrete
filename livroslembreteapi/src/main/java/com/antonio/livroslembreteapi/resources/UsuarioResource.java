package com.antonio.livroslembreteapi.resources;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.antonio.livroslembreteapi.models.Usuario;
import com.antonio.livroslembreteapi.services.UsuarioService;

@XmlAccessorType(XmlAccessType.NONE)
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Path("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;

	@GET
	public Page<Usuario> findAll(@QueryParam("page") int page, @QueryParam("size") int size) {
		return usuarioService.findAll(new PageRequest(page, size));
	}

	@GET
	@Path("/login")
	public Response logar(@QueryParam("email") String email, @QueryParam("senha") String senha) {
		return usuarioService.logar(email, senha);
	}
	
	@GET
	@Path("/{id}")
	public Response findOne(@PathParam("id") Long id) throws URISyntaxException {
		return usuarioService.findOne(id);
	}

	@POST
	public Response save(Usuario usuario) throws URISyntaxException {
		return usuarioService.save(usuario);
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, Usuario usuario) throws URISyntaxException {
		return usuarioService.update(id, usuario);
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) throws URISyntaxException {
		return usuarioService.delete(id);
	}
}