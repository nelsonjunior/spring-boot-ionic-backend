package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.exceptions.ObjectNotFoundException;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional 
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente cliente = find(obj.getId());
		updateData(cliente, obj);
		return repo.save(cliente);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir há entidades relacionadas!");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direct){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direct), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO dto) {
		Cliente cliente = new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
		return cliente;
	}
	
	private void updateData(Cliente cliente, Cliente obj) {
		cliente.setNome(obj.getNome());
		cliente.setEmail(obj.getEmail());
	}

	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.toEnum(dto.getTipoCliente()));
		Cidade cidade = new Cidade(dto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemente(), dto.getBairro(), dto.getCep(), cliente, cidade );
		
		cliente.getEnderecos().add(endereco);
		
		cliente.getTelefones().add(dto.getTelefone1());
		
		if(dto.getTelefone2() != null) {
			cliente.getTelefones().add(dto.getTelefone2());
		}
		if(dto.getTelefone3() != null) {
			cliente.getTelefones().add(dto.getTelefone3());
		}
		
		return cliente;
	}
}
