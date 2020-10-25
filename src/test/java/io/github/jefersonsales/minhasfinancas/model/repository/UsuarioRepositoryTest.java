package io.github.jefersonsales.minhasfinancas.model.repository;

import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveVerificarAExistenciadeUmEmail(){
        //cenário
        Usuario usurio = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        repository.save(usurio);

        //ação/ execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertTrue(result);
    }
}