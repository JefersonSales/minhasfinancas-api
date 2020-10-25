package io.github.jefersonsales.minhasfinancas.model.repository;

import io.github.jefersonsales.minhasfinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
