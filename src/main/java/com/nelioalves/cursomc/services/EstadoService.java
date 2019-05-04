package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.exceptions.ObjectNotFoundException;
import com.nelioalves.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	public Estado find(Integer id) {
		Optional<Estado> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
	}
	
	public Estado findByNome(String nome) {
		Estado estado = repo.findByNome(nome);
		if (estado == null) {
			new ObjectNotFoundException("Objeto não encontrado! Nome: " + nome + ", Tipo: " + Estado.class.getName());
		}
		return estado;
	}

	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}
}
