package io.github.jefersonsales.minhasfinancas.resources;

import io.github.jefersonsales.minhasfinancas.DTO.AtualizaStatusDTO;
import io.github.jefersonsales.minhasfinancas.DTO.LancamentoDTO;
import io.github.jefersonsales.minhasfinancas.exception.RegraNegocioException;
import io.github.jefersonsales.minhasfinancas.model.entity.Lancamento;
import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import io.github.jefersonsales.minhasfinancas.model.enums.StatusLancamento;
import io.github.jefersonsales.minhasfinancas.model.enums.TipoLancamento;
import io.github.jefersonsales.minhasfinancas.service.LancamentoService;
import io.github.jefersonsales.minhasfinancas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResources {

  @Autowired
  private LancamentoService service;

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping
  public ResponseEntity buscar(
          @RequestParam(value = "descricao", required = false) String descricao,
          @RequestParam(value = "mes", required = false) Integer mes,
          @RequestParam(value = "ano", required = false) Integer ano,
          @RequestParam("usuario") Long idUsuario
          ) {
    Lancamento lancamentoFiltro = new Lancamento();
    lancamentoFiltro.setDescricao(descricao);
    lancamentoFiltro.setMes(mes);
    lancamentoFiltro.setAno(ano);


    Optional<Usuario> usuario = usuarioService.obterPorID(idUsuario);
    if (!usuario.isPresent()) {
      return ResponseEntity.badRequest().body("Não foi póssível realizar a consulta. Usuário não encontrado para o id informado");
    } else {
      lancamentoFiltro.setUsuario(usuario.get());
    }

    List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
    return ResponseEntity.ok(lancamentos);
  }

  @PostMapping
  public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
    try {
      Lancamento entidade = converter(dto);
      Lancamento lancamentoSalvo = service.salvar(entidade);
      return new ResponseEntity(lancamentoSalvo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
    return service.obterPorId(id).map(entity -> {
      try {
        Lancamento lancamento = converter(dto);
        lancamento.setId(entity.getId());
        service.atualizar(lancamento);
        return ResponseEntity.ok(lancamento);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }

    }).orElseGet(() -> new ResponseEntity("Lançamento não encontrdo na base da dados.", HttpStatus.BAD_REQUEST));
  }


  @PutMapping("{id}/atualiza-status")
  public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto){
    return service.obterPorId(id).map(entity -> {
      StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
      if(statusSelecionado == null){
        return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido");
      }
      try {
        entity.setStatus(statusSelecionado);
        service.atualizar(entity);
        return ResponseEntity.ok(entity);
      }catch (RegraNegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }

    }).orElseGet(() -> new ResponseEntity("Lançamento não encontrdo na base da dados.", HttpStatus.BAD_REQUEST));
  }


  @DeleteMapping("{id}")
  public ResponseEntity deletar(@PathVariable("id") Long id) {
    return service.obterPorId(id).map(entidade -> {
      service.deletar(entidade);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }).orElseGet(() ->
            new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
  }


  private Lancamento converter(LancamentoDTO dto) {
    Lancamento lancamento = new Lancamento();
    lancamento.setId(dto.getId());
    lancamento.setDescricao(dto.getDescricao());
    lancamento.setAno(dto.getAno());
    lancamento.setMes(dto.getMes());
    lancamento.setValor(dto.getValor());

    Usuario usuario = usuarioService.obterPorID(dto.getUsuario())
            .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o id informado"));

    lancamento.setUsuario(usuario);
    if(dto.getTipo() != null){
      lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
    }

    if (dto.getStatus() != null){
      lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
    }
    return lancamento;
  }
}
