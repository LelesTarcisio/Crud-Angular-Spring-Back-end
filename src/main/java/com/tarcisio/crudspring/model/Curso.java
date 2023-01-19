package com.tarcisio.crudspring.model;

import org.hibernate.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Gerar todos os getters e setters
@Entity // Especificando a classe como uma entidade que vai fazer o mapeamento com o banco de dados
public class Curso {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO) //Gerar ID automaticamento quando fizermos um insert de um novo curso
    @JsonProperty("_id") // Transformará o id em _id 
    private long id;

    @Column(length=200, nullable = false)
    private String nome;

    @Column(length=10, nullable = false)
    private String categoria;

}
