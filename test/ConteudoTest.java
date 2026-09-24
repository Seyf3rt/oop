import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe espelho de Conteudo.
 *
 * Planos cobertos (ver PLANOS_DE_TESTE.md):
 *   PL14 - Superclasse Conteudo
 *
 * Conteudo é uma classe concreta comum (sem abstract nesta fase), então pode ser
 * instanciada direto no teste.
 */
public class ConteudoTest {

    private Conteudo conteudo;

    @BeforeEach
    public void montarCenario() {
        conteudo = new Conteudo("Faixa bônus", 125);
    }

    @Test
    @DisplayName("Título vazio deve ser rejeitado")
    public void pl14Caso1_tituloVazioDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Conteudo("", 125));
    }

    @Test
    @DisplayName("Título nulo deve ser rejeitado")
    public void pl14Caso2_tituloNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Conteudo(null, 125));
    }

    @Test
    @DisplayName("Duração zero deve ser rejeitada")
    public void pl14Caso3_duracaoZeroDeveSerRejeitada() {
        assertThrows(IllegalArgumentException.class, () -> new Conteudo("Faixa bônus", 0));
    }

    @Test
    @DisplayName("Dados válidos criam o conteúdo sem nenhuma reprodução")
    public void pl14Caso4_dadosValidosCriamOConteudo() {
        assertTrue(conteudo.getId() > 0);
        assertEquals("Faixa bônus", conteudo.getTitulo());
        assertEquals(125, conteudo.getDuracaoSegundos());
        assertEquals(0, conteudo.getReproducoes());
    }

    @Test
    @DisplayName("setTitulo com valor inválido lança exceção e mantém o título anterior")
    public void pl14Caso5_setTituloInvalidoMantemOTituloAnterior() {
        assertThrows(IllegalArgumentException.class, () -> conteudo.setTitulo("   "));
        assertEquals("Faixa bônus", conteudo.getTitulo());
    }

    @Test
    @DisplayName("setDuracaoSegundos negativo lança exceção e mantém a duração anterior")
    public void pl14Caso6_setDuracaoNegativaMantemADuracaoAnterior() {
        assertThrows(IllegalArgumentException.class, () -> conteudo.setDuracaoSegundos(-1));
        assertEquals(125, conteudo.getDuracaoSegundos());
    }

    @Test
    @DisplayName("toString mostra id, título e duração em segundos")
    public void pl14Caso7_toStringMostraIdTituloEDuracao() {
        assertEquals("[" + conteudo.getId() + "] Faixa bônus (125s)", conteudo.toString());
    }

    @Test
    @DisplayName("reproduzir() soma uma reprodução a cada chamada")
    public void pl14Caso8_reproduzirSomaUmaReproducao() {
        conteudo.reproduzir();
        conteudo.reproduzir();

        assertEquals(2, conteudo.getReproducoes());
    }

    @Test
    @DisplayName("getDuracaoFormatada fica na superclasse e formata mm:ss")
    public void pl14Caso9_getDuracaoFormatadaFormataMinutosESegundos() {
        assertEquals("02:05", conteudo.getDuracaoFormatada());
    }
}
