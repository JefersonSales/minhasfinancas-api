package io.github.jefersonsales.minhasfinancas.service.impl;

import io.github.jefersonsales.minhasfinancas.DTO.UserDTO;
import io.github.jefersonsales.minhasfinancas.exception.ErroAutenticacao;
import io.github.jefersonsales.minhasfinancas.exception.RegraNegocioException;
import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import io.github.jefersonsales.minhasfinancas.model.repository.UsuarioRepository;
import io.github.jefersonsales.minhasfinancas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public List<UserDTO> allUser(Pageable pageable) {
        return  usuarioRepository.findAll(pageable)
                .stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (!usuario.isPresent()){
            throw new ErroAutenticacao("Usuário não encontrado para o email informado");
        }

        if (!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida");
        }
    return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = usuarioRepository.existsByEmail(email);
        if(existe){
            throw new RegraNegocioException("Já existe um Usuário cadastrado com este e-mail.");
        }
    }

    @Override
    public Optional<Usuario> obterPorID(Long id) {
        return usuarioRepository.findById(id);
    }
}
