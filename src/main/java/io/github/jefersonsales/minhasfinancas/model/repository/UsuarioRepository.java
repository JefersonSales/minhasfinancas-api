package io.github.jefersonsales.minhasfinancas.model.repository;

import io.github.jefersonsales.minhasfinancas.DTO.UsuarioDTO;
import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

     boolean existsByEmail(String email);
     Optional<Usuario> findByEmail(String email);
}
