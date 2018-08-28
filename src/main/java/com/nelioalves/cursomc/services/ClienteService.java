package com.nelioalves.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.exceptions.ObjectNotFoundException;
import com.nelioalves.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
