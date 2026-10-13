import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Plano de testes - Bebida
 *
 * | Caso | Descrição                  | Entrada                          | Saída esperada            |
 * |------|----------------------------|----------------------------------|---------------------------|
 * | 1    | Bebida alcoólica           | Chopp 12,00, 500 ml, alcoólico   | 15,00                     |
 * | 2*   | Bebida sem álcool          | Refri 6,00, 350 ml               | 6,00                      |
 * | 3*   | Volume zero                | volume 0 ml                      | IllegalArgumentException  |
 * | 4*   | Desconto em bebida         | Chopp 15,00 com 20% de desconto  | 12,00                     |
 *
 * (*) casos próprios
 */
public class BebidaTest {

    private Bebida chopp;

    @BeforeEach
    public void montarCenario() {
        chopp = new Bebida("Chopp", 12.0, 500, true);
    }

    @Test
    @DisplayName("Chopp de 12,00, 500 ml, alcoólico")
    public void caso1_bebidaAlcoolica() {
        assertEquals(15.0, chopp.calcularPreco(), 0.001);
    }

    @Test
    @DisplayName("Bebida sem álcool custa o preço base")
    public void caso2_bebidaSemAlcool() {
        Bebida refri = new Bebida("Refri", 6.0, 350, false);
        assertEquals(6.0, refri.calcularPreco(), 0.001);
    }

    @Test
    @DisplayName("Volume zero")
    public void caso3_volumeZero() {
        assertThrows(IllegalArgumentException.class, () -> new Bebida("Refri", 6.0, 0, false));
    }

    @Test
    @DisplayName("Desconto aplicado sobre o preço com acréscimo")
    public void caso4_descontoEmBebida() {
        assertEquals(12.0, chopp.calcularPrecoComDesconto(20), 0.001);
    }
}
