import org.example.model.Pedido;
import org.example.repository.PedidoRepository;
import org.example.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    private Pedido pedido;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        pedido = new Pedido();
        pedido.setNumeroControle("12345");
        pedido.setNomeProduto("Produto Exemplo");
        pedido.setValorUnitario(BigDecimal.valueOf(100.0));
        pedido.setQuantidade(6);
        pedido.setCodigoCliente(1L);
        pedido.setDataCadastro(LocalDateTime.now());
    }

    @Test
    public void criarPedido_sucesso() {
        // Dado que o pedido não existe
        when(pedidoRepository.findByNumeroControle(pedido.getNumeroControle())).thenReturn(Collections.emptyList());

        Pedido resultado = pedidoService.criarPedido(pedido);

        // Verificações
        assertNotNull(resultado);
        assertEquals(pedido.getNumeroControle(), resultado.getNumeroControle());
        assertEquals(100.0 * 6 * 0.95, resultado.getValorTotal()); // Desconto de 5%

        // Verificar se o pedido foi salvo
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void criarPedido_numeroControleJaExiste() {
        // Simular pedido existente
        when(pedidoRepository.findByNumeroControle(pedido.getNumeroControle()))
                .thenReturn(Collections.singletonList(pedido));

        assertThrows(RuntimeException.class, () -> pedidoService.criarPedido(pedido));
    }

    @Test
    public void criarPedido_quantidadeNaoInformada() {
        // Ajustar o pedido para ter quantidade nula
        pedido.setQuantidade(null);

        when(pedidoRepository.findByNumeroControle(pedido.getNumeroControle())).thenReturn(Collections.emptyList());

        Pedido resultado = pedidoService.criarPedido(pedido);

        // Verificações
        assertNotNull(resultado);
        assertEquals(1, resultado.getQuantidade()); // Deve assumir quantidade 1 por padrão
        assertEquals(100.0 * 1, resultado.getValorTotal());
    }

    @Test
    public void criarPedido_dataCadastroNaoInformada() {
        // Ajustar o pedido para ter data de cadastro nula
        pedido.setDataCadastro(null);

        when(pedidoRepository.findByNumeroControle(pedido.getNumeroControle())).thenReturn(Collections.emptyList());

        Pedido resultado = pedidoService.criarPedido(pedido);

        // Verificações
        assertNotNull(resultado);
        assertNotNull(resultado.getDataCadastro()); // Deve assumir a data atual
        assertEquals(LocalDate.now(), resultado.getDataCadastro());
    }

    @Test
    public void consultarPorNumeroControle_sucesso() {
        // Simular retorno do repositório
        when(pedidoRepository.findByNumeroControle("12345"))
                .thenReturn(Collections.singletonList(pedido));

        List<Pedido> pedidos = pedidoService.consultarPorNumeroControle("12345");

        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
        assertEquals("12345", pedidos.get(0).getNumeroControle());
    }

    @Test
    public void consultarPorNumeroControle_vazio() {
        // Simular retorno vazio do repositório
        when(pedidoRepository.findByNumeroControle("54321")).thenReturn(Collections.emptyList());

        List<Pedido> pedidos = pedidoService.consultarPorNumeroControle("54321");

        assertTrue(pedidos.isEmpty());
    }

    @Test
    public void consultarPorDataCadastro_sucesso() {
        // Simular retorno do repositório
        when(pedidoService.consultarPorData(LocalDate.now()))
                .thenReturn(Collections.singletonList(pedido));

        List<Pedido> pedidos = pedidoService.consultarPorData(LocalDate.now());

        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
        assertEquals(LocalDate.now(), pedidos.get(0).getDataCadastro());
    }

    @Test
    public void consultarPorDataCadastro_vazio() {
        // Simular retorno vazio do repositório
        when(pedidoService.consultarPorData(LocalDate.of(2022, 1, 1))).thenReturn(Collections.emptyList());

        List<Pedido> pedidos = pedidoService.consultarPorData(LocalDate.of(2022, 1, 1));

        assertTrue(pedidos.isEmpty());
    }

    @Test
    public void consultarTodos_sucesso() {
        // Simular retorno do repositório
        when(pedidoRepository.findAll()).thenReturn(Collections.singletonList(pedido));

        List<Pedido> pedidos = pedidoService.consultarTodos();

        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
    }

    @Test
    public void consultarTodos_vazio() {
        // Simular retorno vazio do repositório
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Pedido> pedidos = pedidoService.consultarTodos();

        assertTrue(pedidos.isEmpty());
    }
}