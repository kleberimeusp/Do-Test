package org.example.repository;

import org.example.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByNumeroControle(String numeroControle);
    List<Pedido> findByDataCadastro(LocalDate dataCadastro);
}

