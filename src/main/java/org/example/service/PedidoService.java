package org.example.service;


import org.example.model.Pedido;
import org.example.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Método para criar um pedido
    public Pedido criarPedido(Pedido pedido) {
        // Verificar se o número de controle já existe
        List<Pedido> pedidosExistentes = pedidoRepository.findByNumeroControle(pedido.getNumeroControle());
        if (!pedidosExistentes.isEmpty()) {
            throw new RuntimeException("Número de controle já existe.");
        }

        // Configurar valores padrão
        if (pedido.getDataCadastro() == null) {
            pedido.setDataCadastro(LocalDateTime.now());
        }
        if (pedido.getQuantidade() == null) {
            pedido.setQuantidade(1);
        }

        // Calcular valor total com descontos
        BigDecimal quantidade = BigDecimal.valueOf(pedido.getQuantidade());
        BigDecimal valorTotal = pedido.getValorTotal().multiply(quantidade);

        if (pedido.getQuantidade() >= 10) {
            valorTotal = valorTotal.multiply(BigDecimal.valueOf(0.9)); // 10% de desconto
        } else if (pedido.getQuantidade() > 5) {
            valorTotal = valorTotal.multiply(BigDecimal.valueOf(0.95)); // 5% de desconto
        }
        pedido.setValorTotal(valorTotal);

        // Salvar pedido no banco de dados
        return pedidoRepository.save(pedido);
    }

    // Método para consultar pedidos por número de controle
    public List<Pedido> consultarPorNumeroControle(String numeroControle) {
        return pedidoRepository.findByNumeroControle(numeroControle);
    }

    // Método para consultar pedidos por data de cadastro
    public List<Pedido> consultarPorDataCadastro(LocalDate dataCadastro) {
        return pedidoRepository.findByDataCadastro(dataCadastro);
    }

    // Método para consultar todos os pedidos
    public List<Pedido> consultarTodos() {
        return pedidoRepository.findAll();
    }
}