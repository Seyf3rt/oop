import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Plano de testes - CentralCobranca
 *
 * | Caso | Descrição                     | Entrada                                         | Saída esperada            |
 * |------|-------------------------------|-------------------------------------------------|---------------------------|
 * | 1    | Total de pedido + assinatura  | pedido de 64,00 + assinatura 19,90 por 12 meses | total a receber 266,98    |
 * | 2*   | Registrar nulo                | registrar(null)                                 | IllegalArgumentException  |
 * | 3*   | Central vazia                 | nenhuma cobrança                                | total 0,00, quantidade 0  |
 * | 4*   | Quantidade de cobranças       | registra pedido e assinatura                    | quantidade 2              |
 *
 * (*) casos próprios
 */
public class CentralCobrancaTest {

    private CentralCobranca central;
    private Pedido pedido;
    private AssinaturaClube assinatura;

    @BeforeEach
    public void montarCenario() {
        central = new CentralCobranca();
        Cliente ana = new Cliente("Ana Souza", "ana@furb.br", 20);

        pedido = new Pedido(ana);
        pedido.adicionarItem(new Prato("Xis Salada", 30.0, 'M'));
        pedido.adicionarItem(new Bebida("Refri", 6.0, 350, false));
        pedido.adicionarItem(new Sobremesa("Pudim", 8.0, 150));

        assinatura = new AssinaturaClube(ana, 19.90, 12);
    }

    @Test
    @DisplayName("Pedido de 64,00 + assinatura de 19,90 por 12 meses")
    public void caso1_totalAReceber() {
        central.registrar(pedido);
        central.registrar(assinatura);
        assertEquals(266.98, central.calcularTotalAReceber(), 0.001);
    }

    @Test
    @DisplayName("Registrar cobrança nula")
    public void caso2_registrarNulo() {
        assertThrows(IllegalArgumentException.class, () -> central.registrar(null));
    }

    @Test
    @DisplayName("Central vazia")
    public void caso3_centralVazia() {
        assertEquals(0.0, central.calcularTotalAReceber(), 0.001);
        assertEquals(0, central.getQuantidadeCobrancas());
    }

    @Test
    @DisplayName("Quantidade de cobranças registradas")
    public void caso4_quantidade() {
        central.registrar(pedido);
        central.registrar(assinatura);
        assertEquals(2, central.getQuantidadeCobrancas());
    }
}
