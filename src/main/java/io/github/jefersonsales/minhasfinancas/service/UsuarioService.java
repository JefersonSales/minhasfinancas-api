package io.github.jefersonsales.minhasfinancas.service;

import io.github.jefersonsales.minhasfinancas.DTO.UsuarioDTO;
import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface UsuarioService {

    List<UsuarioDTO> allUser(Pageable pageable);

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

}
