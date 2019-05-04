package com.nelioalves.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional(readOnly = true)
	Cidade findByNome(String nome);

	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoID ORDER BY obj.nome")
	@Transactional(readOnly = true)
	List<Cidade> findCidades(@Param("estadoID") Integer estadoID);
}
