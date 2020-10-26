package io.github.jefersonsales.minhasfinancas.resources;

import io.github.jefersonsales.minhasfinancas.DTO.AtualizaStatusDTO;
import io.github.jefersonsales.minhasfinancas.DTO.UsuarioDTO;
import io.github.jefersonsales.minhasfinancas.exception.ErroAutenticacao;
import io.github.jefersonsales.minhasfinancas.exception.RegraNegocioException;
import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import io.github.jefersonsales.minhasfinancas.model.enums.StatusLancamento;
import io.github.jefersonsales.minhasfinancas.service.LancamentoService;
import io.github.jefersonsales.minhasfinancas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioResource {

   @Autowired
   private UsuarioService service;

   @Autowired
   private LancamentoService lancamentoService;

   @PostMapping("/autenticar")
   public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
       try {
           Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
           return ResponseEntity.ok(usuarioAutenticado);
       }catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
       }
   }


   @PostMapping
   public ResponseEntity salvar(@RequestBody UsuarioDTO dto){

       Usuario usuario = Usuario.builder()
               .nome(dto.getNome())
               .email(dto.getEmail())
               .senha(dto.getSenha()).build();

       try {
           Usuario usuarioSalvo = service.salvarUsuario(usuario);
           return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
       }catch (RegraNegocioException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
   }

   @GetMapping("{id}/saldo")
   public ResponseEntity obterSaldo(@PathVariable("id") Long id){
     Optional<Usuario> usuario = service.obterPorID(id);

     if(!usuario.isPresent()){
       return new ResponseEntity(HttpStatus.NOT_FOUND);
     }
     BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
     return ResponseEntity.ok(saldo);
   }

}
