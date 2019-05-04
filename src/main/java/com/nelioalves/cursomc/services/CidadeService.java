package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.exceptions.ObjectNotFoundException;
import com.nelioalves.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;

	public Cidade find(Integer id) {
		Optional<Cidade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}

	public Cidade findByNome(String nome) {
		Cidade estado = repo.findByNome(nome);
		if (estado == null) {
			new ObjectNotFoundException("Objeto não encontrado! Nome: " + nome + ", Tipo: " + Cidade.class.getName());
		}
		return estado;
	}
	
	public List<Cidade> findCidades(Integer estadoID){
		return repo.findCidades(estadoID);
	}
}
