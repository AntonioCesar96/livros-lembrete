package com.antonio.livroslembreteapi.resources;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import com.antonio.livroslembreteapi.models.Livro;
import com.antonio.livroslembreteapi.services.LivroService;
import com.antonio.livroslembreteapi.services.UploadService;

@XmlAccessorType(XmlAccessType.NONE)
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Path("/livros")
public class LivroResource {

	@Autowired
	private UploadService uploadService;

	@Autowired
	private LivroService livroService;

	@GET
	public Page<Livro> findAll(@QueryParam("page") int page, @QueryParam("size") int size) {
		return livroService.findAll(new PageRequest(page, size));
	}

	@POST
	public Response save(Livro livro) throws URISyntaxException {
		return livroService.save(livro);
	}

	@GET
	@Path("/{id}")
	public Response findOne(@PathParam("id") Long id) throws URISyntaxException {
		return livroService.findOne(id);
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, Livro livro) throws URISyntaxException {
		return livroService.update(id, livro);
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) throws URISyntaxException {
		return livroService.delete(id);
	}

	@POST
	@Path("/imagem-base64")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ResponseWithURL saveImageBase64(@FormParam("fileName") String fileName, @FormParam("base64") String base64) {
		if (fileName != null && base64 != null) {
			try {
				String url = uploadService.upload(base64);
				return ResponseWithURL.Ok("Arquivo recebido com sucesso", url);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseWithURL.Error("Erro ao enviar o arquivo.");
			}
		}
		return ResponseWithURL.Error("Requisição inválida.");
	}
}