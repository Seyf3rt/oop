import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe espelho de Podcast.
 *
 * Planos cobertos (ver PLANOS_DE_TESTE.md):
 *   PL16 - Podcast como subclasse de Conteudo
 */
public class PodcastTest {

    private Podcast podcast;

    @BeforeEach
    public void montarCenario() {
        podcast = new Podcast("Café com Código", 2700, "Ana Souza", 42);
    }

    @Test
    @DisplayName("Episódio zero deve ser rejeitado")
    public void pl16Caso1_episodioZeroDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Podcast("Café com Código", 2700, "Ana Souza", 0));
    }

    @Test
    @DisplayName("Episódio negativo deve ser rejeitado")
    public void pl16Caso2_episodioNegativoDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Podcast("Café com Código", 2700, "Ana Souza", -3));
    }

    @Test
    @DisplayName("Episódio 1 é o menor valor aceito")
    public void pl16Caso3_episodioUmEOMenorValorAceito() {
        Podcast primeiro = new Podcast("Café com Código", 2700, "Ana Souza", 1);

        assertEquals(1, primeiro.getNumeroEpisodio());
    }

    @Test
    @DisplayName("Apresentador vazio deve ser rejeitado")
    public void pl16Caso4_apresentadorVazioDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Podcast("Café com Código", 2700, "", 42));
    }

    @Test
    @DisplayName("Apresentador nulo deve ser rejeitado")
    public void pl16Caso5_apresentadorNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Podcast("Café com Código", 2700, null, 42));
    }

    @Test
    @DisplayName("A validação de título herdada de Conteudo vale para o podcast")
    public void pl16Caso6_validacaoDeTituloHerdadaValeParaOPodcast() {
        assertThrows(IllegalArgumentException.class, () -> new Podcast("", 2700, "Ana Souza", 42));
    }

    @Test
    @DisplayName("A validação de duração herdada de Conteudo vale para o podcast")
    public void pl16Caso7_validacaoDeDuracaoHerdadaValeParaOPodcast() {
        assertThrows(IllegalArgumentException.class, () -> new Podcast("Café com Código", 0, "Ana Souza", 42));
    }

    @Test
    @DisplayName("Dados válidos criam o podcast com as partes herdada e própria")
    public void pl16Caso8_dadosValidosCriamOPodcast() {
        assertTrue(podcast.getId() > 0);
        assertEquals("Café com Código", podcast.getTitulo());
        assertEquals(2700, podcast.getDuracaoSegundos());
        assertEquals("Ana Souza", podcast.getApresentador());
        assertEquals(42, podcast.getNumeroEpisodio());
    }

    @Test
    @DisplayName("toString reaproveita a parte comum de Conteudo e acrescenta episódio e apresentador")
    public void pl16Caso9_toStringReaproveitaAParteComumEAcrescentaEpisodioEApresentador() {
        String esperado = "[" + podcast.getId() + "] Café com Código (2700s) - Ep. 42, com Ana Souza";

        assertEquals(esperado, podcast.toString());
    }

    @Test
    @DisplayName("reproduzir() herdado de Conteudo conta as reproduções do podcast")
    public void pl16Caso10_reproduzirHerdadoContaAsReproducoes() {
        podcast.reproduzir();

        assertEquals(1, podcast.getReproducoes());
    }

    @Test
    @DisplayName("setNumeroEpisodio(0) lança exceção e mantém o episódio anterior")
    public void pl16Caso11_setNumeroEpisodioInvalidoMantemOAnterior() {
        assertThrows(IllegalArgumentException.class, () -> podcast.setNumeroEpisodio(0));
        assertEquals(42, podcast.getNumeroEpisodio());
    }

    @Test
    @DisplayName("Música e podcast compartilham o mesmo contador de id")
    public void pl16Caso12_musicaEPodcastCompartilhamOMesmoContador() {
        Musica antes = new Musica("Faixa 1", 100, "Artista", "Álbum");
        Podcast meio = new Podcast("Café com Código", 2700, "Ana Souza", 43);
        Musica depois = new Musica("Faixa 2", 100, "Artista", "Álbum");

        assertEquals(antes.getId() + 1, meio.getId());
        assertEquals(antes.getId() + 2, depois.getId());
    }
}
