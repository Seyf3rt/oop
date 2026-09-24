import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe espelho de Playlist.
 *
 * Planos cobertos (ver PLANOS_DE_TESTE.md):
 *   PL03 - Validar Playlist.adicionar(musica)
 *   PL04 - Validar Playlist.getNaPosicao(indice)
 *   PL05 - Validar Playlist.removerNaPosicao(indice)
 *   PL10 - Validar construtor de Playlist
 */
public class PlaylistTest {

    /**
     * Quantidade usada nos testes de crescimento. Não é mais uma capacidade: depois
     * da troca do array por ArrayList a playlist não tem tamanho máximo.
     */
    private static final int MUITAS = 100;

    private Usuario dono;
    private Playlist playlist;
    private Musica bohemian;
    private Musica hotel;
    private Musica stairway;

    @BeforeEach
    public void montarCenario() {
        dono = new Usuario("Lucas", "lucas@sonora.com");
        playlist = new Playlist("Clássicos do Rock", dono);

        bohemian = new Musica("Bohemian Rhapsody", 355, "Queen", "A Night at the Opera");
        hotel = new Musica("Hotel California", 391, "Eagles", "Hotel California");
        stairway = new Musica("Stairway to Heaven", 482, "Led Zeppelin", "Led Zeppelin IV");
    }

    /** Deixa a playlist com [bohemian, hotel, stairway], nessa ordem. */
    private void adicionarAsTres() {
        playlist.adicionar(bohemian);
        playlist.adicionar(hotel);
        playlist.adicionar(stairway);
    }

    /**
     * Deixa a playlist com MUITAS músicas. A Playlist não impede música repetida, então
     * a mesma instância é usada MUITAS vezes: assim não são criados 100 objetos Musica
     * (o contador de id é global, compartilhado por todos os testes).
     */
    private void adicionarMuitas() {
        for (int i = 0; i < MUITAS; i++) {
            playlist.adicionar(bohemian);
        }
    }

    // ------------------------------------------------------------------
    // PL03 - Validar Playlist.adicionar(musica)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Adicionar em playlist vazia retorna true e a quantidade vira um")
    public void pl03Caso1_adicionarEmPlaylistVaziaRetornaTrueEQuantidadeViraUm() {
        assertTrue(playlist.adicionar(bohemian));
        assertEquals(1, playlist.getQuantidade());
    }

    @Test
    @DisplayName("Adicionar três músicas sobe a quantidade para três e mantém a ordem")
    public void pl03Caso2_adicionarTresMusicasSobeAQuantidadeEMantemAOrdem() {
        adicionarAsTres();

        assertEquals(3, playlist.getQuantidade());
        assertEquals(bohemian, playlist.getNaPosicao(0));
        assertEquals(hotel, playlist.getNaPosicao(1));
        assertEquals(stairway, playlist.getNaPosicao(2));
    }

    @Test
    @DisplayName("Adicionar 100 músicas: todas as adições retornam true")
    public void pl03Caso3_adicionarCemMusicasRetornaTrueEmTodasAsAdicoes() {
        for (int i = 0; i < MUITAS; i++) {
            assertTrue(playlist.adicionar(bohemian), "Falhou na adição de número " + (i + 1));
        }
        assertEquals(MUITAS, playlist.getQuantidade());
    }

    @Test
    @DisplayName("A playlist não tem tamanho máximo: passar de 100 músicas continua funcionando")
    public void pl03Caso4_playlistNaoTemTamanhoMaximo() {
        adicionarMuitas();

        assertTrue(playlist.adicionar(hotel));
        assertEquals(MUITAS + 1, playlist.getQuantidade());
        assertEquals(hotel, playlist.getNaPosicao(MUITAS));
    }

    @Test
    @DisplayName("Adicionar música nula lança IllegalArgumentException e nada é adicionado")
    public void pl03Caso5_adicionarMusicaNulaLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> playlist.adicionar(null));
        assertEquals(0, playlist.getQuantidade());
    }

    // ------------------------------------------------------------------
    // PL04 - Validar Playlist.getNaPosicao(indice)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Posição zero devolve a primeira música adicionada")
    public void pl04Caso1_posicaoZeroDevolveAPrimeiraMusica() {
        adicionarAsTres();

        assertEquals(bohemian, playlist.getNaPosicao(0));
    }

    @Test
    @DisplayName("Última posição válida devolve a última música adicionada")
    public void pl04Caso2_ultimaPosicaoValidaDevolveAUltimaMusica() {
        adicionarAsTres();

        assertEquals(stairway, playlist.getNaPosicao(playlist.getQuantidade() - 1));
    }

    @Test
    @DisplayName("Índice negativo lança IndexOutOfBoundsException")
    public void pl04Caso3_indiceNegativoLancaExcecao() {
        adicionarAsTres();

        assertThrows(IndexOutOfBoundsException.class, () -> playlist.getNaPosicao(-1));
    }

    @Test
    @DisplayName("Índice igual à quantidade lança IndexOutOfBoundsException")
    public void pl04Caso4_indiceIgualAQuantidadeLancaExcecao() {
        adicionarAsTres();

        assertThrows(IndexOutOfBoundsException.class, () -> playlist.getNaPosicao(3));
    }

    @Test
    @DisplayName("Playlist vazia: posição zero lança IndexOutOfBoundsException")
    public void pl04Caso5_playlistVaziaPosicaoZeroLancaExcecao() {
        assertThrows(IndexOutOfBoundsException.class, () -> playlist.getNaPosicao(0));
    }

    // ------------------------------------------------------------------
    // PL05 - Validar Playlist.removerNaPosicao(indice)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Remover do meio: a música seguinte assume a posição e a quantidade cai em um")
    public void pl05Caso1_removerDoMeioReorganizaSemDeixarBuraco() {
        adicionarAsTres();

        playlist.removerNaPosicao(1);

        assertEquals(2, playlist.getQuantidade());
        assertEquals(bohemian, playlist.getNaPosicao(0));
        assertEquals(stairway, playlist.getNaPosicao(1));
    }

    @Test
    @DisplayName("Remover a primeira: a segunda música passa a ocupar a posição zero")
    public void pl05Caso2_removerAPrimeiraFazASegundaOcuparAPosicaoZero() {
        adicionarAsTres();

        playlist.removerNaPosicao(0);

        assertEquals(2, playlist.getQuantidade());
        assertEquals(hotel, playlist.getNaPosicao(0));
        assertEquals(stairway, playlist.getNaPosicao(1));
    }

    @Test
    @DisplayName("Remover a última: a quantidade cai e a antiga posição passa a ser inválida")
    public void pl05Caso3_removerAUltimaTornaAAntigaPosicaoInvalida() {
        adicionarAsTres();

        playlist.removerNaPosicao(2);

        assertEquals(2, playlist.getQuantidade());
        assertEquals(hotel, playlist.getNaPosicao(1));
        assertThrows(IndexOutOfBoundsException.class, () -> playlist.getNaPosicao(2));
    }

    @Test
    @DisplayName("Remoção de posição válida retorna true")
    public void pl05Caso4_remocaoDePosicaoValidaRetornaTrue() {
        adicionarAsTres();

        assertTrue(playlist.removerNaPosicao(1));
    }

    @Test
    @DisplayName("Índice negativo lança IndexOutOfBoundsException e nada é removido")
    public void pl05Caso5_indiceNegativoLancaExcecaoENadaERemovido() {
        adicionarAsTres();

        assertThrows(IndexOutOfBoundsException.class, () -> playlist.removerNaPosicao(-1));
        assertEquals(3, playlist.getQuantidade());
    }

    @Test
    @DisplayName("Índice além da quantidade lança IndexOutOfBoundsException e nada é removido")
    public void pl05Caso6_indiceAlemDaQuantidadeLancaExcecaoENadaERemovido() {
        adicionarAsTres();

        assertThrows(IndexOutOfBoundsException.class, () -> playlist.removerNaPosicao(3));
        assertEquals(3, playlist.getQuantidade());
    }

    @Test
    @DisplayName("A duração total acompanha a remoção")
    public void pl05Caso7_duracaoTotalAcompanhaARemocao() {
        adicionarAsTres();
        assertEquals(355 + 391 + 482, playlist.getDuracaoSegundos());

        playlist.removerNaPosicao(1); // tira Hotel California (391 s)

        assertEquals(355 + 482, playlist.getDuracaoSegundos());
        assertEquals("13:57", playlist.getDuracaoFormatada());
    }

    // ------------------------------------------------------------------
    // PL10 - Validar construtor de Playlist
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Título vazio deve ser rejeitado")
    public void pl10Caso1_tituloVazioDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Playlist("", dono));
    }

    @Test
    @DisplayName("Título nulo deve ser rejeitado")
    public void pl10Caso2_tituloNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Playlist(null, dono));
    }

    @Test
    @DisplayName("Dono nulo deve ser rejeitado")
    public void pl10Caso3_donoNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Playlist("Clássicos do Rock", null));
    }

    @Test
    @DisplayName("Dados válidos criam a playlist vazia")
    public void pl10Caso4_dadosValidosCriamAPlaylistVazia() {
        assertTrue(playlist.getId() > 0);
        assertEquals("Clássicos do Rock", playlist.getTitulo());
        assertEquals(dono, playlist.getDono());
        assertEquals(0, playlist.getQuantidade());
        assertEquals(0, playlist.getDuracaoSegundos());
    }
}
