import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe espelho de Plataforma.
 *
 * Planos cobertos (ver PLANOS_DE_TESTE.md):
 *   PL06 - Validar Plataforma.buscarMusica(titulo) e buscarMusica(id)
 *   PL11 - Validar Plataforma.cadastrarMusica e cadastrarUsuario
 *   PL13 - Acervo em ArrayList e a associação reflexiva pela Plataforma
 *   PL17 - Podcasts na Plataforma
 *
 * Observação: depois da Fase 05 a Plataforma guarda tudo em ArrayList e localiza os
 * objetos pelo id, não mais pela posição num array dimensionado pelos contadores
 * static. Cadastrar uma música criada bem antes passou a funcionar.
 */
public class PlataformaTest {

    private Plataforma plataforma;
    private Musica bohemian;
    private Musica hotel;
    private Usuario lucas;

    @BeforeEach
    public void montarCenario() {
        plataforma = new Plataforma();

        bohemian = new Musica("Bohemian Rhapsody", 355, "Queen", "A Night at the Opera");
        plataforma.cadastrarMusica(bohemian);

        hotel = new Musica("Hotel California", 391, "Eagles", "Hotel California");
        plataforma.cadastrarMusica(hotel);

        lucas = new Usuario("Lucas", "lucas@sonora.com");
        plataforma.cadastrarUsuario(lucas);
    }

    // ------------------------------------------------------------------
    // PL06 - Validar Plataforma.buscarMusica(titulo) e buscarMusica(id)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Buscar por id de música cadastrada devolve a música")
    public void pl06Caso1_buscarPorIdDeMusicaCadastradaDevolveAMusica() {
        assertEquals(bohemian, plataforma.buscarMusica(bohemian.getId()));
        assertEquals(hotel, plataforma.buscarMusica(hotel.getId()));
    }

    @Test
    @DisplayName("Buscar por título de música cadastrada devolve a música")
    public void pl06Caso2_buscarPorTituloDeMusicaCadastradaDevolveAMusica() {
        assertEquals(hotel, plataforma.buscarMusica("Hotel California"));
    }

    @Test
    @DisplayName("Buscar por título ignora maiúsculas e minúsculas")
    public void pl06Caso3_buscarPorTituloIgnoraMaiusculasEMinusculas() {
        assertEquals(bohemian, plataforma.buscarMusica("bohemian rhapsody"));
        assertEquals(bohemian, plataforma.buscarMusica("BOHEMIAN RHAPSODY"));
    }

    @Test
    @DisplayName("Buscar por id inexistente devolve null")
    public void pl06Caso4_buscarPorIdInexistenteDevolveNull() {
        // Os ids de conteúdo são sequenciais a partir de 1 e o contador nunca diminui
        // nos testes, então "contagem + 1" é um id que ainda não foi dado a ninguém.
        int idInexistente = Conteudo.getContagem() + 1;

        assertNull(plataforma.buscarMusica(idInexistente));
    }

    @Test
    @DisplayName("Buscar por título inexistente devolve null")
    public void pl06Caso5_buscarPorTituloInexistenteDevolveNull() {
        assertNull(plataforma.buscarMusica("Música que não existe"));
    }

    // ------------------------------------------------------------------
    // PL11 - Validar Plataforma.cadastrarMusica e cadastrarUsuario
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Música nula não é cadastrada (retorna false)")
    public void pl11Caso1_musicaNulaNaoECadastrada() {
        assertFalse(plataforma.cadastrarMusica(null));
    }

    @Test
    @DisplayName("Música válida é cadastrada (retorna true) e passa a ser encontrada")
    public void pl11Caso2_musicaValidaECadastradaEPassaASerEncontrada() {
        Musica stairway = new Musica("Stairway to Heaven", 482, "Led Zeppelin", "Led Zeppelin IV");

        assertTrue(plataforma.cadastrarMusica(stairway));
        assertEquals(stairway, plataforma.buscarMusica(stairway.getId()));
        assertEquals(stairway, plataforma.buscarMusica("Stairway to Heaven"));
    }

    @Test
    @DisplayName("Usuário nulo não é cadastrado (retorna false)")
    public void pl11Caso3_usuarioNuloNaoECadastrado() {
        assertFalse(plataforma.cadastrarUsuario(null));
    }

    @Test
    @DisplayName("Usuário válido é cadastrado (retorna true)")
    public void pl11Caso4_usuarioValidoECadastrado() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");

        assertTrue(plataforma.cadastrarUsuario(ana));
    }

    // ------------------------------------------------------------------
    // PL13 - Acervo em ArrayList e a associação reflexiva pela Plataforma
    // ------------------------------------------------------------------

    @Test
    @DisplayName("O total de músicas conta só o que a plataforma guarda")
    public void pl13Caso1_totalDeMusicasContaSoOAcervoDaPlataforma() {
        assertEquals(2, plataforma.getTotalMusicas());

        plataforma.cadastrarMusica(new Musica("Stairway to Heaven", 482, "Led Zeppelin", "Led Zeppelin IV"));

        assertEquals(3, plataforma.getTotalMusicas());
    }

    @Test
    @DisplayName("Uma plataforma nova nasce com o acervo vazio, mesmo com músicas já criadas")
    public void pl13Caso2_plataformaNovaNasceVazia() {
        Plataforma outra = new Plataforma();

        assertEquals(0, outra.getTotalMusicas());
        assertEquals(0, outra.getTotalUsuarios());
        assertNull(outra.buscarMusica(bohemian.getId()));
    }

    @Test
    @DisplayName("Músicas criadas antes do cadastro não se sobrescrevem")
    public void pl13Caso3_musicasCriadasAntesDoCadastroNaoSeSobrescrevem() {
        Musica primeira = new Musica("Come Together", 259, "The Beatles", "Abbey Road");
        Musica segunda = new Musica("Let It Be", 243, "The Beatles", "Let It Be");

        assertTrue(plataforma.cadastrarMusica(primeira));
        assertTrue(plataforma.cadastrarMusica(segunda));

        assertEquals(primeira, plataforma.buscarMusica(primeira.getId()));
        assertEquals(segunda, plataforma.buscarMusica(segunda.getId()));
    }

    @Test
    @DisplayName("A mesma música não é cadastrada duas vezes")
    public void pl13Caso4_mesmaMusicaNaoECadastradaDuasVezes() {
        assertFalse(plataforma.cadastrarMusica(bohemian));
        assertEquals(2, plataforma.getTotalMusicas());
    }

    @Test
    @DisplayName("Excluir música tira do acervo sem mexer nos ids das próximas")
    public void pl13Caso5_excluirMusicaNaoMexeNosIdsDasProximas() {
        int idAntes = Conteudo.getContagem();

        assertTrue(plataforma.excluirMusica(hotel.getId()));

        assertNull(plataforma.buscarMusica(hotel.getId()));
        assertEquals(1, plataforma.getTotalMusicas());

        Musica nova = new Musica("Let It Be", 243, "The Beatles", "Let It Be");
        assertEquals(idAntes + 1, nova.getId());
    }

    @Test
    @DisplayName("Um usuário passa a seguir outro pela plataforma")
    public void pl13Caso6_usuarioPassaASeguirOutroPelaPlataforma() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");
        plataforma.cadastrarUsuario(ana);

        plataforma.seguirUsuario(lucas.getId(), ana.getId());

        assertTrue(lucas.segue(ana));
        assertEquals(1, lucas.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Seguir usuário inexistente lança IllegalArgumentException")
    public void pl13Caso7_seguirUsuarioInexistenteLancaExcecao() {
        int idInexistente = Usuario.getContagem() + 1;

        assertThrows(IllegalArgumentException.class,
                () -> plataforma.seguirUsuario(lucas.getId(), idInexistente));
    }

    @Test
    @DisplayName("Deixar de seguir pela plataforma desfaz a ligação")
    public void pl13Caso8_deixarDeSeguirPelaPlataformaDesfazALigacao() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");
        plataforma.cadastrarUsuario(ana);
        plataforma.seguirUsuario(lucas.getId(), ana.getId());

        plataforma.deixarDeSeguirUsuario(lucas.getId(), ana.getId());

        assertFalse(lucas.segue(ana));
        assertEquals(0, lucas.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Excluir um usuário também remove as ligações de quem o seguia")
    public void pl13Caso9_excluirUsuarioRemoveAsLigacoesDeQuemOSeguia() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");
        plataforma.cadastrarUsuario(ana);
        plataforma.seguirUsuario(lucas.getId(), ana.getId());

        assertTrue(plataforma.excluirUsuario(ana.getId()));

        assertFalse(lucas.segue(ana));
        assertEquals(0, lucas.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Excluir um usuário leva junto as playlists de que ele era dono")
    public void pl13Caso10_excluirUsuarioLevaJuntoAsPlaylistsDele() {
        plataforma.cadastrarPlaylist("Clássicos do Rock", lucas.getId());
        assertEquals(1, plataforma.getTotalPlaylists());

        assertTrue(plataforma.excluirUsuario(lucas.getId()));

        assertEquals(0, plataforma.getTotalPlaylists());
    }

    @Test
    @DisplayName("A playlist criada pela plataforma recebe o dono informado")
    public void pl13Caso11_playlistCriadaRecebeODonoInformado() {
        assertTrue(plataforma.cadastrarPlaylist("Clássicos do Rock", lucas.getId()));

        Playlist playlist = plataforma.buscarPlaylist(Playlist.getContagem());

        assertEquals(lucas, playlist.getDono());
        assertEquals("Clássicos do Rock", playlist.getTitulo());
    }

    @Test
    @DisplayName("Adicionar música à playlist pela plataforma usa o id, não a posição")
    public void pl13Caso12_adicionarMusicaAPlaylistPelaPlataformaUsaOId() {
        plataforma.cadastrarPlaylist("Clássicos do Rock", lucas.getId());
        Playlist playlist = plataforma.buscarPlaylist(Playlist.getContagem());

        assertTrue(plataforma.addMusicaPlaylist(playlist.getId(), hotel.getId()));

        assertEquals(1, playlist.getQuantidade());
        assertEquals(hotel, playlist.getNaPosicao(0));
    }

    @Test
    @DisplayName("Remover da playlist uma música que não está nela devolve false")
    public void pl13Caso13_removerMusicaForaDaPlaylistDevolveFalse() {
        plataforma.cadastrarPlaylist("Clássicos do Rock", lucas.getId());
        Playlist playlist = plataforma.buscarPlaylist(Playlist.getContagem());
        plataforma.addMusicaPlaylist(playlist.getId(), hotel.getId());

        assertFalse(plataforma.excluirMusicaPlaylist(playlist.getId(), bohemian.getId()));
        assertEquals(1, playlist.getQuantidade());
    }

    // ------------------------------------------------------------------
    // PL17 - Podcasts na Plataforma
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Podcast nulo não é cadastrado (retorna false)")
    public void pl17Caso1_podcastNuloNaoECadastrado() {
        assertFalse(plataforma.cadastrarPodcast(null));
    }

    @Test
    @DisplayName("Podcast válido é cadastrado e passa a ser encontrado pelo id")
    public void pl17Caso2_podcastValidoECadastradoEEncontrado() {
        Podcast podcast = new Podcast("Café com Código", 2700, "Ana Souza", 1);

        assertTrue(plataforma.cadastrarPodcast(podcast));
        assertEquals(podcast, plataforma.buscarPodcast(podcast.getId()));
    }

    @Test
    @DisplayName("O mesmo podcast não é cadastrado duas vezes")
    public void pl17Caso3_mesmoPodcastNaoECadastradoDuasVezes() {
        Podcast podcast = new Podcast("Café com Código", 2700, "Ana Souza", 1);
        plataforma.cadastrarPodcast(podcast);

        assertFalse(plataforma.cadastrarPodcast(podcast));
    }

    @Test
    @DisplayName("Excluir podcast tira da plataforma; excluir de novo devolve false")
    public void pl17Caso4_excluirPodcastTiraDaPlataforma() {
        Podcast podcast = new Podcast("Café com Código", 2700, "Ana Souza", 1);
        plataforma.cadastrarPodcast(podcast);

        assertTrue(plataforma.excluirPodcast(podcast.getId()));
        assertNull(plataforma.buscarPodcast(podcast.getId()));
        assertFalse(plataforma.excluirPodcast(podcast.getId()));
    }

    @Test
    @DisplayName("Podcasts e músicas ficam em listas separadas")
    public void pl17Caso5_podcastsEMusicasFicamEmListasSeparadas() {
        Podcast podcast = new Podcast("Café com Código", 2700, "Ana Souza", 1);
        plataforma.cadastrarPodcast(podcast);

        assertNull(plataforma.buscarMusica(podcast.getId()));
        assertNull(plataforma.buscarPodcast(bohemian.getId()));
        assertEquals(2, plataforma.getTotalMusicas());
    }

    @Test
    @DisplayName("A listagem de podcasts usa o toString do Podcast")
    public void pl17Caso6_listagemDePodcastsUsaOToString() {
        Podcast podcast = new Podcast("Café com Código", 2700, "Ana Souza", 1);
        plataforma.cadastrarPodcast(podcast);

        assertTrue(plataforma.getTodosPodcasts().contains(podcast.toString()));
    }
}
