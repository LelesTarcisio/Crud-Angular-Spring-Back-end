package com.tarcisio.crudspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarcisio.crudspring.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
    
}
