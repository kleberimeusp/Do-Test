package org.example.repository;

import org.example.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByNumeroControle(String numeroControle);

    @Query(value = "SELECT * FROM pedido WHERE data_cadastro >= :dataInicio AND data_cadastro < :dataFim", nativeQuery = true)
    List<Pedido> findByDataCadastroBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}

