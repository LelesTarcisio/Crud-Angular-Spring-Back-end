package com.tarcisio.crudspring.controller;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tarcisio.crudspring.model.Curso;
import com.tarcisio.crudspring.repository.CursoRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/cursos")
@AllArgsConstructor // Importando o construtor, opção do Lombok
public class CursoController {

  //Configurando CORS
  @Configuration
public class CorsConfiguration implements WebMvcConfigurer {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
  
          @Override
          public void addCorsMappings(CorsRegistry registry) {
              registry.addMapping("/**")
                      .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
                      .allowedHeaders("*")
                      .allowedOrigins("*");
          }
      };
  }
}

    private CursoRepository cursoRepository;// Injetando o CursoRpositopry no controller

    // Listar os grupos
    @GetMapping
    public List<Curso> listaCursos() {
        return cursoRepository.findAll();
    }

      // Pegando os dados por ID
      @GetMapping("/{id}")
      public ResponseEntity <Curso> findById(@PathVariable long id) {
          return cursoRepository.findById(id)
          .map(recordFound -> ResponseEntity.ok().body(recordFound)) // Caso encontre o id retornará ok
          .orElse(ResponseEntity.notFound().build()); // caso não 404
      }

    // Salvando os dados no BD
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Com ResponseStatus consigo impor o status correto de 201 para criação de curso.
    public Curso criarCursos(@RequestBody Curso curso) {
          return cursoRepository.save(curso); 
    }

    // Editando os dados
    @PutMapping({"/{id}"})
    public ResponseEntity <Curso> atualizacao (@PathVariable ("id") Long identificador, @RequestBody Curso curso){ //Temos as informações que precisamos na assinatura do método
        return cursoRepository.findById(identificador)
            .map(recordFound -> {
              recordFound.setNome(curso.getNome());  // Se encontrar o registro, vai pegar o reg que veio da base e atualizar com as informações que estão vindo do request
              recordFound.setCategoria(curso.getCategoria());  
              Curso atualizacao = cursoRepository.save(recordFound);
              return ResponseEntity.ok().body(atualizacao);
            }) 
            .orElse(ResponseEntity.notFound().build());
    }

    // Deletando os dados
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCurso(@PathVariable ("id") Long identificador){
        return cursoRepository.findById(identificador)
            .map(recordFound -> {
                cursoRepository.deleteById(identificador);
              return ResponseEntity.noContent().<Void>build(); // para retornar nada temos o noContent
            }) 
            .orElse(ResponseEntity.notFound().build());
    }

}
