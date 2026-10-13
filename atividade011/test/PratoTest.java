import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Plano de testes - Prato
 *
 * | Caso | Descrição                    | Entrada                               | Saída esperada            |
 * |------|------------------------------|---------------------------------------|---------------------------|
 * | 1    | Tamanho inválido             | tamanho 'X'                           | IllegalArgumentException  |
 * | 2    | Desconto em prato médio      | 30,00, tamanho M, 10% de desconto     | 35,10                     |
 * | 3    | Desconto acima de 100%       | desconto de 120%                      | IllegalArgumentException  |
 * | 4*   | Prato pequeno                | 30,00, tamanho P                      | 30,00                     |
 * | 5*   | Prato grande                 | 30,00, tamanho G                      | 48,00                     |
 * | 6*   | Preço base zero              | preço 0                               | IllegalArgumentException  |
 *
 * (*) casos próprios
 */
public class PratoTest {

    private Prato xisMedio;

    @BeforeEach
    public void montarCenario() {
        xisMedio = new Prato("Xis Salada", 30.0, 'M');
    }

    @Test
    @DisplayName("Prato com tamanho 'X'")
    public void caso1_tamanhoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Prato("Xis", 30.0, 'X'));
    }

    @Test
    @DisplayName("Prato de 30,00, tamanho M, com 10% de desconto")
    public void caso2_descontoPratoMedio() {
        assertEquals(35.10, xisMedio.calcularPrecoComDesconto(10), 0.001);
    }

    @Test
    @DisplayName("Desconto de 120%")
    public void caso3_descontoAcimaDe100() {
        assertThrows(IllegalArgumentException.class, () -> xisMedio.calcularPrecoComDesconto(120));
    }

    @Test
    @DisplayName("Prato pequeno custa o preço base")
    public void caso4_pratoPequeno() {
        assertEquals(30.0, new Prato("Xis", 30.0, 'P').calcularPreco(), 0.001);
    }

    @Test
    @DisplayName("Prato grande custa 60% a mais")
    public void caso5_pratoGrande() {
        assertEquals(48.0, new Prato("Xis", 30.0, 'G').calcularPreco(), 0.001);
    }

    @Test
    @DisplayName("Preço base zero")
    public void caso6_precoZero() {
        assertThrows(IllegalArgumentException.class, () -> new Prato("Xis", 0, 'M'));
    }
}
