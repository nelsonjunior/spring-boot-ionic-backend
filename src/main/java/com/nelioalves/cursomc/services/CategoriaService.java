package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.exceptions.ObjectNotFoundException;
import com.nelioalves.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria categoria = find(obj.getId());
		updateData(categoria, obj);
		return repo.save(categoria);
	}

	private void updateData(Categoria categoria, Categoria obj) {
		categoria.setNome(obj.getNome());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direct){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direct), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}
