package org.example.controller;

import org.example.model.Pedido;
import org.example.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Endpoint para criar um pedido (suporta JSON e XML)
    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.criarPedido(pedido);
        return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
    }

    // Endpoint para consultar pedido por número controle
    @GetMapping(value = "/numero/{numeroControle}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<Pedido>> consultarPorNumeroControle(@PathVariable String numeroControle) {
        List<Pedido> pedidos = pedidoService.consultarPorNumeroControle(numeroControle);
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Endpoint para consultar pedido por data de cadastro
    @GetMapping(value = "/data", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<Pedido>> consultarPorDataCadastro(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCadastro) {
        List<Pedido> pedidos = pedidoService.consultarPorDataCadastro(dataCadastro);
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Endpoint para consultar todos os pedidos
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<Pedido>> consultarTodos() {
        List<Pedido> pedidos = pedidoService.consultarTodos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}