import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Plano de testes - Cliente
 *
 * | Caso | Descrição                               | Entrada                                  | Saída esperada              |
 * |------|-----------------------------------------|------------------------------------------|-----------------------------|
 * | 1    | Idade inválida                          | idade -1                                 | IllegalArgumentException    |
 * | 2    | E-mail sem @                            | "ana.furb.br"                            | IllegalArgumentException    |
 * | 3    | Indicação credita cashback no indicador | Beto criado com a Ana como indicadora    | saldo da Ana = 10,00        |
 * | 4    | Usar mais cashback do que o saldo       | Ana com 10,00 usa 15,00                  | SaldoInsuficienteException  |
 * | 5    | equals ignora maiúsculas no e-mail      | "ana@furb.br" e "ANA@FURB.BR"            | true                        |
 * | 6*   | Usar cashback dentro do saldo           | Ana com 10,00 usa 4,00                   | saldo = 6,00                |
 * | 7*   | Indicador nulo                          | construtor com indicadoPor = null        | IllegalArgumentException    |
 * | 8*   | Maioridade                              | 18 anos / 17 anos                        | true / false                |
 * | 9*   | Creditar valor zero                     | creditarCashback(0)                      | IllegalArgumentException    |
 *
 * (*) casos próprios
 */
public class ClienteTest {

    private Cliente ana;

    @BeforeEach
    public void montarCenario() {
        ana = new Cliente("Ana Souza", "ana@furb.br", 20);
    }

    @Test
    @DisplayName("Cliente com idade -1")
    public void caso1_idadeNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("Ana", "ana@furb.br", -1));
    }

    @Test
    @DisplayName("Cliente com e-mail sem @")
    public void caso2_emailSemArroba() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("Ana", "ana.furb.br", 20));
    }

    @Test
    @DisplayName("Beto indicado pela Ana credita 10,00 na Ana")
    public void caso3_indicacaoCreditaCashback() {
        Cliente beto = new Cliente("Beto Lima", "beto@furb.br", 17, ana);
        assertEquals(10.0, ana.getSaldoCashback(), 0.001);
        assertEquals(ana, beto.getIndicadoPor());
    }

    @Test
    @DisplayName("Ana com 10,00 tenta usar 15,00")
    public void caso4_saldoInsuficiente() {
        ana.creditarCashback(10);
        assertThrows(SaldoInsuficienteException.class, () -> ana.usarCashback(15));
    }

    @Test
    @DisplayName("equals ignora maiúsculas e minúsculas no e-mail")
    public void caso5_equalsIgnoraCaixa() {
        Cliente outra = new Cliente("Ana Maria", "ANA@FURB.BR", 30);
        assertTrue(ana.equals(outra));
    }

    @Test
    @DisplayName("Usar cashback dentro do saldo desconta do saldo")
    public void caso6_usarCashbackValido() throws SaldoInsuficienteException {
        ana.creditarCashback(10);
        ana.usarCashback(4);
        assertEquals(6.0, ana.getSaldoCashback(), 0.001);
    }

    @Test
    @DisplayName("Indicador nulo")
    public void caso7_indicadorNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("Beto", "beto@furb.br", 17, null));
    }

    @Test
    @DisplayName("Maioridade a partir de 18 anos")
    public void caso8_maioridade() {
        assertTrue(new Cliente("Caio", "caio@furb.br", 18).isMaiorDeIdade());
        assertFalse(new Cliente("Beto", "beto@furb.br", 17).isMaiorDeIdade());
    }

    @Test
    @DisplayName("Creditar valor zero")
    public void caso9_creditarZero() {
        assertThrows(IllegalArgumentException.class, () -> ana.creditarCashback(0));
    }
}
