package io.github.jefersonsales.minhasfinancas.resources;

import io.github.jefersonsales.minhasfinancas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserResources {

    @Autowired
    UsuarioService usuarioService;

//    public List<?> allUser(@PageableDefault(size = 10, page = 0, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
//
//    }


}
