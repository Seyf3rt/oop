import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Plano de testes - Sobremesa
 *
 * | Caso | Descrição                 | Entrada                       | Saída esperada            |
 * |------|---------------------------|-------------------------------|---------------------------|
 * | 1    | Preço proporcional ao peso| Pudim 8,00 por 100 g, 150 g   | 12,00                     |
 * | 2*   | Exatamente 100 g          | Pudim 8,00 por 100 g, 100 g   | 8,00                      |
 * | 3*   | Peso zero                 | 0 g                           | IllegalArgumentException  |
 *
 * (*) casos próprios
 */
public class SobremesaTest {

    private Sobremesa pudim;

    @BeforeEach
    public void montarCenario() {
        pudim = new Sobremesa("Pudim", 8.0, 150);
    }

    @Test
    @DisplayName("Pudim de 8,00 por 100 g, com 150 g")
    public void caso1_precoProporcional() {
        assertEquals(12.0, pudim.calcularPreco(), 0.001);
    }

    @Test
    @DisplayName("Sobremesa de 100 g custa o preço base")
    public void caso2_cemGramas() {
        assertEquals(8.0, new Sobremesa("Pudim", 8.0, 100).calcularPreco(), 0.001);
    }

    @Test
    @DisplayName("Peso zero")
    public void caso3_pesoZero() {
        assertThrows(IllegalArgumentException.class, () -> new Sobremesa("Pudim", 8.0, 0));
    }
}
