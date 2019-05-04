package com.nelioalves.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.dto.CidadeDTO;
import com.nelioalves.cursomc.dto.EstadoDTO;
import com.nelioalves.cursomc.services.CidadeService;
import com.nelioalves.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {

		List<Estado> estados = service.findAll();

		return ResponseEntity.ok(toDTO(estados));
	}

	private List<EstadoDTO> toDTO(List<Estado> estados) {
		return estados.stream().map(obj -> toDTO(obj)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EstadoDTO> find(@PathVariable Integer id) {

		Estado estado = service.find(id);

		return ResponseEntity.ok(toDTO(estado));
	}
	
	@RequestMapping(value = "/{id}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer id) {

		List<Cidade> cidades = cidadeService.findCidades(id);
		List<CidadeDTO> listaDTO = cidades.stream().map(obj -> toDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok(listaDTO);
	}

	private CidadeDTO toDTO(Cidade cidade) {
		return new CidadeDTO(cidade.getId(), cidade.getNome());
	}

	@RequestMapping(value = "/nome", method = RequestMethod.GET)
	public ResponseEntity<EstadoDTO> findByNome(@RequestParam(value = "value") String nome) {

		Estado estado = service.findByNome(nome);

		return ResponseEntity.ok(toDTO(estado));
	}

	private EstadoDTO toDTO(Estado estado) {
		return new EstadoDTO(estado.getId(), estado.getNome());
	}

}
