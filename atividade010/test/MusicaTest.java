import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe espelho de Musica.
 *
 * Planos cobertos (ver PLANOS_DE_TESTE.md):
 *   PL01 - Validar Musica.getDuracaoFormatada()
 *   PL02 - Validar construtor de Musica com dados inválidos
 *   PL07 - Validar Musica.reproduzir()
 *   PL08 - Contadores de id (bônus) - casos 1 a 4
 *   PL15 - Musica como subclasse de Conteudo (álbum e toString sobrescrito)
 *
 * Observação: desde a herança o id da música vem do contador static de Conteudo,
 * que é compartilhado com Podcast e não é zerado entre os testes (todas as classes
 * de teste rodam na mesma JVM). Por isso os casos de id comparam ids relativos
 * (um em relação ao outro), nunca valores absolutos como 1, 2, 3.
 */
public class MusicaTest {

    private Musica bohemian;

    @BeforeEach
    public void montarCenario() {
        bohemian = new Musica("Bohemian Rhapsody", 355, "Queen", "A Night at the Opera");
    }

    // ------------------------------------------------------------------
    // PL01 - Validar Musica.getDuracaoFormatada()
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Duração com minutos e segundos")
    public void pl01Caso1_duracaoComMinutosESegundos() {
        Musica musica = new Musica("Faixa", 125, "Artista", "Álbum");
        assertEquals("02:05", musica.getDuracaoFormatada());
    }

    @Test
    @DisplayName("Duração redonda em minutos")
    public void pl01Caso2_duracaoRedondaEmMinutos() {
        Musica musica = new Musica("Faixa", 90, "Artista", "Álbum");
        assertEquals("01:30", musica.getDuracaoFormatada());
    }

    @Test
    @DisplayName("Menos de um minuto, com zero à esquerda")
    public void pl01Caso3_menosDeUmMinutoComZeroAEsquerda() {
        Musica musica = new Musica("Faixa", 5, "Artista", "Álbum");
        assertEquals("00:05", musica.getDuracaoFormatada());
    }

    @Test
    @DisplayName("Dois dígitos nos minutos")
    public void pl01Caso4_doisDigitosNosMinutos() {
        Musica musica = new Musica("Faixa", 600, "Artista", "Álbum");
        assertEquals("10:00", musica.getDuracaoFormatada());
    }

    @Test
    @DisplayName("Valor logo abaixo de dez minutos")
    public void pl01Caso5_valorLogoAbaixoDeDezMinutos() {
        Musica musica = new Musica("Faixa", 599, "Artista", "Álbum");
        assertEquals("09:59", musica.getDuracaoFormatada());
    }

    // ------------------------------------------------------------------
    // PL02 - Validar construtor de Musica com dados inválidos
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Título vazio deve ser rejeitado")
    public void pl02Caso1_tituloVazioDeveSerRejeitado() {
        // O 2º argumento do assertThrows é o trecho de código que deve estourar a
        // exceção; a sintaxe "() -> ..." só embrulha esse trecho pro JUnit executar.
        assertThrows(IllegalArgumentException.class, () -> new Musica("", 355, "Queen", "A Night at the Opera"));
    }

    @Test
    @DisplayName("Título nulo deve ser rejeitado")
    public void pl02Caso2_tituloNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Musica(null, 355, "Queen", "A Night at the Opera"));
    }

    @Test
    @DisplayName("Artista vazio deve ser rejeitado")
    public void pl02Caso3_artistaVazioDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Musica("Bohemian Rhapsody", 355, "", "A Night at the Opera"));
    }

    @Test
    @DisplayName("Duração zero deve ser rejeitada")
    public void pl02Caso4_duracaoZeroDeveSerRejeitada() {
        assertThrows(IllegalArgumentException.class, () -> new Musica("Bohemian Rhapsody", 0, "Queen", "A Night at the Opera"));
    }

    @Test
    @DisplayName("Duração negativa deve ser rejeitada")
    public void pl02Caso5_duracaoNegativaDeveSerRejeitada() {
        assertThrows(IllegalArgumentException.class, () -> new Musica("Bohemian Rhapsody", -10, "Queen", "A Night at the Opera"));
    }

    @Test
    @DisplayName("Dados válidos criam a música")
    public void pl02Caso6_dadosValidosCriamAMusica() {
        Musica musica = new Musica("Bohemian Rhapsody", 355, "Queen", "A Night at the Opera");

        assertNotNull(musica);
        assertTrue(musica.getId() > 0);
        assertEquals("Bohemian Rhapsody", musica.getTitulo());
        assertEquals("Queen", musica.getArtista());
        assertEquals(355, musica.getDuracaoSegundos());
    }

    @Test
    @DisplayName("Título só com espaços deve ser rejeitado")
    public void pl02Caso7_tituloSoComEspacosDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Musica("   ", 355, "Queen", "A Night at the Opera"));
    }

    @Test
    @DisplayName("A mensagem da exceção descreve o erro")
    public void pl02Caso8_mensagemDaExcecaoDescreveOErro() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> new Musica("Bohemian Rhapsody", -30, "Queen", "A Night at the Opera"));

        assertNotNull(erro.getMessage());
        assertFalse(erro.getMessage().trim().isEmpty());
        assertTrue(erro.getMessage().contains("-30"));
    }

    // ------------------------------------------------------------------
    // PL07 - Validar Musica.reproduzir()
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Música recém-criada começa com zero reproduções")
    public void pl07Caso1_musicaRecemCriadaComecaComZeroReproducoes() {
        assertEquals(0, bohemian.getReproducoes());
    }

    @Test
    @DisplayName("Uma chamada de reproduzir() aumenta o contador em um")
    public void pl07Caso2_umaChamadaAumentaOContadorEmUm() {
        int antes = bohemian.getReproducoes();

        bohemian.reproduzir();

        assertEquals(antes + 1, bohemian.getReproducoes());
    }

    @Test
    @DisplayName("Três chamadas de reproduzir() resultam em três reproduções")
    public void pl07Caso3_tresChamadasResultamEmTresReproducoes() {
        bohemian.reproduzir();
        bohemian.reproduzir();
        bohemian.reproduzir();

        assertEquals(3, bohemian.getReproducoes());
    }

    @Test
    @DisplayName("Reproduzir uma música não altera o contador de outra")
    public void pl07Caso4_reproduzirNaoAlteraOContadorDeOutraMusica() {
        Musica outra = new Musica("Hotel California", 391, "Eagles", "Hotel California");

        bohemian.reproduzir();
        bohemian.reproduzir();

        assertEquals(2, bohemian.getReproducoes());
        assertEquals(0, outra.getReproducoes());
    }

    // ------------------------------------------------------------------
    // PL08 (bônus) - Contadores de id - casos 1 a 4
    // (casos 5 e 6, sobre os ids de Usuário, estão em UsuarioTest)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("A segunda música criada recebe o id da primeira mais um")
    public void pl08Caso1_segundaMusicaRecebeOIdDaPrimeiraMaisUm() {
        Musica primeira = new Musica("Faixa 1", 100, "Artista", "Álbum");
        Musica segunda = new Musica("Faixa 2", 100, "Artista", "Álbum");

        assertEquals(primeira.getId() + 1, segunda.getId());
    }

    @Test
    @DisplayName("Três músicas criadas em sequência têm ids consecutivos")
    public void pl08Caso2_tresMusicasEmSequenciaTemIdsConsecutivos() {
        Musica primeira = new Musica("Faixa 1", 100, "Artista", "Álbum");
        Musica segunda = new Musica("Faixa 2", 100, "Artista", "Álbum");
        Musica terceira = new Musica("Faixa 3", 100, "Artista", "Álbum");

        assertEquals(primeira.getId() + 1, segunda.getId());
        assertEquals(primeira.getId() + 2, terceira.getId());
    }

    @Test
    @DisplayName("Criar um usuário entre duas músicas não interfere nos ids de Música")
    public void pl08Caso3_usuarioNoMeioNaoInterfereNosIdsDeMusica() {
        Musica antes = new Musica("Faixa 1", 100, "Artista", "Álbum");
        Usuario usuario = new Usuario("Lucas", "lucas@sonora.com");
        Musica depois = new Musica("Faixa 2", 100, "Artista", "Álbum");

        assertNotNull(usuario);
        assertEquals(antes.getId() + 1, depois.getId());
    }

    @Test
    @DisplayName("O id da música é o valor atual do contador de Conteudo")
    public void pl08Caso4_idDaMusicaEOValorAtualDoContadorDeConteudo() {
        Musica musica = new Musica("Faixa", 100, "Artista", "Álbum");

        assertEquals(Conteudo.getContagem(), musica.getId());
    }

    // ------------------------------------------------------------------
    // PL15 - Musica como subclasse de Conteudo
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Álbum vazio deve ser rejeitado")
    public void pl15Caso1_albumVazioDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Musica("Bohemian Rhapsody", 355, "Queen", ""));
    }

    @Test
    @DisplayName("Álbum nulo deve ser rejeitado")
    public void pl15Caso2_albumNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Musica("Bohemian Rhapsody", 355, "Queen", null));
    }

    @Test
    @DisplayName("Artista nulo deve ser rejeitado")
    public void pl15Caso3_artistaNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class,
                () -> new Musica("Bohemian Rhapsody", 355, null, "A Night at the Opera"));
    }

    @Test
    @DisplayName("Dados válidos guardam artista e álbum")
    public void pl15Caso4_dadosValidosGuardamArtistaEAlbum() {
        assertEquals("Queen", bohemian.getArtista());
        assertEquals("A Night at the Opera", bohemian.getAlbum());
    }

    @Test
    @DisplayName("toString reaproveita a parte comum de Conteudo e acrescenta artista e álbum")
    public void pl15Caso5_toStringReaproveitaAParteComumEAcrescentaArtistaEAlbum() {
        String esperado = "[" + bohemian.getId() + "] Bohemian Rhapsody (355s) - Queen (A Night at the Opera)";

        assertEquals(esperado, bohemian.toString());
    }

    @Test
    @DisplayName("setAlbum com valor inválido lança exceção e mantém o álbum anterior")
    public void pl15Caso6_setAlbumInvalidoMantemOAlbumAnterior() {
        assertThrows(IllegalArgumentException.class, () -> bohemian.setAlbum("  "));
        assertEquals("A Night at the Opera", bohemian.getAlbum());
    }

    @Test
    @DisplayName("Setters herdados de Conteudo funcionam na música")
    public void pl15Caso7_settersHerdadosFuncionamNaMusica() {
        bohemian.setTitulo("Bohemian Rhapsody (Remaster)");
        bohemian.setDuracaoSegundos(354);

        assertEquals("Bohemian Rhapsody (Remaster)", bohemian.getTitulo());
        assertEquals(354, bohemian.getDuracaoSegundos());
        assertEquals("05:54", bohemian.getDuracaoFormatada());
    }
}
