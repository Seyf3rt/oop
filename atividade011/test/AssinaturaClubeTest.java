import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Plano de testes - AssinaturaClube
 *
 * | Caso | Descrição                       | Entrada                  | Saída esperada            |
 * |------|---------------------------------|--------------------------|---------------------------|
 * | 1*   | Menos de 12 meses, sem desconto | 19,90 por 11 meses       | 218,90                    |
 * | 2*   | 12 meses, 15% de desconto       | 19,90 por 12 meses       | 202,98                    |
 * | 3*   | Zero meses                      | 0 meses                  | IllegalArgumentException  |
 * | 4*   | Cliente nulo                    | cliente null             | IllegalArgumentException  |
 *
 * (*) casos próprios (o PDF não lista casos obrigatórios para esta classe)
 */
public class AssinaturaClubeTest {

    private Cliente ana;

    @BeforeEach
    public void montarCenario() {
        ana = new Cliente("Ana Souza", "ana@furb.br", 20);
    }

    @Test
    @DisplayName("11 meses não tem desconto")
    public void caso1_semDesconto() {
        assertEquals(218.90, new AssinaturaClube(ana, 19.90, 11).calcularValorCobrar(), 0.001);
    }

    @Test
    @DisplayName("12 meses tem 15% de desconto")
    public void caso2_comDesconto() {
        assertEquals(202.98, new AssinaturaClube(ana, 19.90, 12).calcularValorCobrar(), 0.001);
    }

    @Test
    @DisplayName("Zero meses")
    public void caso3_zeroMeses() {
        assertThrows(IllegalArgumentException.class, () -> new AssinaturaClube(ana, 19.90, 0));
    }

    @Test
    @DisplayName("Cliente nulo")
    public void caso4_clienteNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AssinaturaClube(null, 19.90, 12));
    }
}
