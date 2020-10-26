package io.github.jefersonsales.minhasfinancas.service;

import io.github.jefersonsales.minhasfinancas.DTO.UserDTO;
import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;


public interface UsuarioService {

    List<UserDTO> allUser(Pageable pageable);

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

    Optional<Usuario> obterPorID(Long id);

}
