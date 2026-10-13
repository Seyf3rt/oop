import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Plano de testes - Pedido
 *
 * | Caso | Descrição                       | Entrada                                         | Saída esperada                      |
 * |------|---------------------------------|-------------------------------------------------|-------------------------------------|
 * | 1    | Pedido com taxa de entrega      | Xis 30,00 (M) + Refri 6,00 + Pudim 8,00 (150 g) | subtotal 57,00, taxa 7,00, total 64 |
 * | 2    | Frete grátis                    | dois Xis de 40,00 tamanho G                     | taxa 0,00, total 128,00             |
 * | 3    | Menor de idade pede chopp       | cliente de 17 anos adiciona Chopp               | IllegalArgumentException            |
 * | 4    | Quantidade zero                 | adicionarItem(xis, 0)                           | IllegalArgumentException            |
 * | 5    | Finalizar sem itens             | pedido vazio                                    | IllegalStateException               |
 * | 6    | Cashback ao finalizar           | subtotal 57,00                                  | cashback do cliente +2,85           |
 * | 7    | Numeração sequencial            | dois pedidos criados em sequência               | números consecutivos                |
 * | 8*   | Finalizar duas vezes            | finalizar() de novo                             | IllegalStateException               |
 * | 9*   | Adicionar após finalizar        | adicionarItem depois de finalizar()             | IllegalStateException               |
 * | 10*  | Item nulo                       | adicionarItem(null)                             | IllegalArgumentException            |
 *
 * (*) casos próprios
 *
 * Observação: proximoNumero é static e não volta para 1 entre os testes,
 * então o caso 7 compara a diferença entre os números, não o valor absoluto.
 */
public class PedidoTest {

    private Cliente ana;
    private Pedido pedido;
    private Prato xis;
    private Bebida refri;
    private Sobremesa pudim;

    @BeforeEach
    public void montarCenario() {
        ana = new Cliente("Ana Souza", "ana@furb.br", 20);
        pedido = new Pedido(ana);
        xis = new Prato("Xis Salada", 30.0, 'M');
        refri = new Bebida("Refri", 6.0, 350, false);
        pudim = new Sobremesa("Pudim", 8.0, 150);
    }

    private void montarPedidoDe57() {
        pedido.adicionarItem(xis);
        pedido.adicionarItem(refri);
        pedido.adicionarItem(pudim);
    }

    @Test
    @DisplayName("Xis + Refri + Pudim: subtotal 57,00, taxa 7,00, total 64,00")
    public void caso1_pedidoComTaxa() {
        montarPedidoDe57();
        assertEquals(57.0, pedido.calcularSubTotal(), 0.001);
        assertEquals(7.0, pedido.calcularTaxaEntrega(), 0.001);
        assertEquals(64.0, pedido.calcularValorCobrar(), 0.001);
    }

    @Test
    @DisplayName("Dois Xis de 40,00 tamanho G: taxa 0,00, total 128,00")
    public void caso2_freteGratis() {
        pedido.adicionarItem(new Prato("Xis", 40.0, 'G'), 2);
        assertEquals(0.0, pedido.calcularTaxaEntrega(), 0.001);
        assertEquals(128.0, pedido.calcularValorCobrar(), 0.001);
    }

    @Test
    @DisplayName("Cliente de 17 anos adiciona um Chopp")
    public void caso3_menorPedeChopp() {
        Pedido pedidoBeto = new Pedido(new Cliente("Beto", "beto@furb.br", 17));
        Bebida chopp = new Bebida("Chopp", 12.0, 500, true);
        assertThrows(IllegalArgumentException.class, () -> pedidoBeto.adicionarItem(chopp));
    }

    @Test
    @DisplayName("Adicionar com quantidade 0")
    public void caso4_quantidadeZero() {
        assertThrows(IllegalArgumentException.class, () -> pedido.adicionarItem(xis, 0));
    }

    @Test
    @DisplayName("Finalizar pedido sem itens")
    public void caso5_finalizarSemItens() {
        assertThrows(IllegalStateException.class, () -> pedido.finalizar());
    }

    @Test
    @DisplayName("Finalizar pedido de 57,00 dá 2,85 de cashback")
    public void caso6_cashbackAoFinalizar() {
        montarPedidoDe57();
        pedido.finalizar();
        assertEquals(2.85, ana.getSaldoCashback(), 0.001);
        assertTrue(pedido.isFinalizado());
    }

    @Test
    @DisplayName("Dois pedidos criados em sequência têm números consecutivos")
    public void caso7_numeracaoSequencial() {
        Pedido primeiro = new Pedido(ana);
        Pedido segundo = new Pedido(ana);
        assertEquals(1, segundo.getNumero() - primeiro.getNumero());
    }

    @Test
    @DisplayName("Finalizar o mesmo pedido duas vezes")
    public void caso8_finalizarDuasVezes() {
        montarPedidoDe57();
        pedido.finalizar();
        assertThrows(IllegalStateException.class, () -> pedido.finalizar());
    }

    @Test
    @DisplayName("Adicionar item depois de finalizar")
    public void caso9_adicionarAposFinalizar() {
        montarPedidoDe57();
        pedido.finalizar();
        assertThrows(IllegalStateException.class, () -> pedido.adicionarItem(refri));
    }

    @Test
    @DisplayName("Adicionar item nulo")
    public void caso10_itemNulo() {
        assertThrows(IllegalArgumentException.class, () -> pedido.adicionarItem(null));
    }
}
