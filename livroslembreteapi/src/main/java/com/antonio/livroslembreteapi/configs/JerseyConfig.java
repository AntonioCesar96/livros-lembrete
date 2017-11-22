package com.antonio.livroslembreteapi.configs;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.antonio.livroslembreteapi.resources.LivroResource;
import com.antonio.livroslembreteapi.resources.UsuarioResource;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(CORSFilter.class);
		packages("com.antonio.livroslembreteapi.resources");
		// register(UsuarioResource.class);
	}
}