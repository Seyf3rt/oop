import java.util.ArrayList;

class Plataforma {
    // Acervo da plataforma. Antes eram arrays de tamanho fixo (500/500/500) posicionados
    // pelos contadores static das outras classes; agora são listas que crescem sozinhas
    // e a busca é sempre pelo id do objeto, nunca pela posição.
    private ArrayList<Musica> musicas = new ArrayList<>();
    private ArrayList<Podcast> podcasts = new ArrayList<>();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Playlist> playlists = new ArrayList<>();

    // ------------------------------------------------------------------
    // Músicas
    // ------------------------------------------------------------------

    public boolean cadastrarMusica(Musica musica) {
        if (musica == null || musicas.contains(musica)) {
            return false;
        }
        return musicas.add(musica);
    }

    public int getTotalMusicas() {
        return musicas.size();
    }

    public String getTodasMusicas() {
        if (musicas.isEmpty()) {
            return "Não há músicas cadastradas.";
        }
        String todasMusicas = "";
        for (Musica musica : musicas) {
            todasMusicas += "\n" + musica;
        }
        return todasMusicas;
    }

    public Musica buscarMusica(int id) {
        for (Musica musica : musicas) {
            if (musica.getId() == id) {
                return musica;
            }
        }
        return null;
    }

    public Musica buscarMusica(String titulo) {
        if (titulo == null) {
            return null;
        }
        for (Musica musica : musicas) {
            if (musica.getTitulo().equalsIgnoreCase(titulo)) {
                return musica;
            }
        }
        return null;
    }

    /** Tira a música do acervo. As playlists que já a tinham continuam com ela. */
    public boolean excluirMusica(int idMusica) {
        Musica musica = buscarMusica(idMusica);
        if (musica == null) {
            return false;
        }
        return musicas.remove(musica);
    }

    // ------------------------------------------------------------------
    // Podcasts
    // ------------------------------------------------------------------

    public boolean cadastrarPodcast(Podcast podcast) {
        if (podcast == null || podcasts.contains(podcast)) {
            return false;
        }
        return podcasts.add(podcast);
    }

    public String getTodosPodcasts() {
        if (podcasts.isEmpty()) {
            return "Não há podcasts cadastrados.";
        }
        String todosPodcasts = "";
        for (Podcast podcast : podcasts) {
            todosPodcasts += "\n" + podcast;
        }
        return todosPodcasts;
    }

    public Podcast buscarPodcast(int id) {
        for (Podcast podcast : podcasts) {
            if (podcast.getId() == id) {
                return podcast;
            }
        }
        return null;
    }

    public boolean excluirPodcast(int idPodcast) {
        Podcast podcast = buscarPodcast(idPodcast);
        if (podcast == null) {
            return false;
        }
        return podcasts.remove(podcast);
    }

    // ------------------------------------------------------------------
    // Usuários
    // ------------------------------------------------------------------

    public boolean cadastrarUsuario(Usuario usuario) {
        if (usuario == null || usuarios.contains(usuario)) {
            return false;
        }
        return usuarios.add(usuario);
    }

    public int getTotalUsuarios() {
        return usuarios.size();
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public String getInfoUsuarios() {
        if (usuarios.isEmpty()) {
            return "Não há usuarios.";
        }
        String infoUsuarios = "";
        for (Usuario usuario : usuarios) {
            if (!infoUsuarios.isEmpty()) {
                infoUsuarios += "\n";
            }
            infoUsuarios += usuario.informacoes();
        }
        return infoUsuarios;
    }

    public String getInfoUsuario(int id) {
        Usuario usuario = exigirUsuario(id);
        return usuario.informacoes();
    }

    public boolean excluirUsuario(int id) {
        Usuario usuario = buscarUsuario(id);
        if (usuario == null) {
            return false;
        }
        // Quem seguia o excluído deixa de segui-lo, senão sobra uma ligação pra um
        // usuário que não está mais na plataforma.
        for (Usuario outro : usuarios) {
            if (outro.segue(usuario)) {
                outro.deixarDeSeguir(usuario);
            }
        }
        // As playlists do usuário saem junto: toda playlist precisa de um dono.
        playlists.removeIf(playlist -> playlist.getDono() == usuario);
        return usuarios.remove(usuario);
    }

    // ------------------------------------------------------------------
    // Associação reflexiva: seguir usuários
    // ------------------------------------------------------------------

    public void seguirUsuario(int idSeguidor, int idSeguido) {
        Usuario seguidor = exigirUsuario(idSeguidor);
        Usuario seguido = exigirUsuario(idSeguido);
        seguidor.seguir(seguido);
    }

    public void deixarDeSeguirUsuario(int idSeguidor, int idSeguido) {
        Usuario seguidor = exigirUsuario(idSeguidor);
        Usuario seguido = exigirUsuario(idSeguido);
        seguidor.deixarDeSeguir(seguido);
    }

    /** Quem o usuário segue (papel -seguindo da associação reflexiva). */
    public String getSeguindo(int id) {
        Usuario usuario = exigirUsuario(id);
        ArrayList<Usuario> seguindo = usuario.getSeguindo();

        if (seguindo.isEmpty()) {
            return usuario.getNome() + " não segue ninguém.";
        }

        String texto = usuario.getNome() + " segue " + seguindo.size() + " usuário(s):";
        for (Usuario outro : seguindo) {
            texto += "\n - " + outro.getNome() + " (id " + outro.getId() + ")";
        }
        return texto;
    }

    /**
     * Quem segue o usuário (papel -seguidores). Como só o lado "seguindo" guarda a
     * coleção, os seguidores são descobertos percorrendo os cadastrados.
     */
    public String getSeguidores(int id) {
        Usuario usuario = exigirUsuario(id);

        String texto = "";
        int quantidade = 0;
        for (Usuario outro : usuarios) {
            if (outro.segue(usuario)) {
                texto += "\n - " + outro.getNome() + " (id " + outro.getId() + ")";
                quantidade++;
            }
        }

        if (quantidade == 0) {
            return usuario.getNome() + " não tem seguidores.";
        }
        return usuario.getNome() + " tem " + quantidade + " seguidor(es):" + texto;
    }

    // ------------------------------------------------------------------
    // Playlists
    // ------------------------------------------------------------------

    public boolean cadastrarPlaylist(String nome, int idDono) {
        Usuario dono = exigirUsuario(idDono);
        return playlists.add(new Playlist(nome, dono));
    }

    public int getTotalPlaylists() {
        return playlists.size();
    }

    public Playlist buscarPlaylist(int id) {
        for (Playlist playlist : playlists) {
            if (playlist.getId() == id) {
                return playlist;
            }
        }
        return null;
    }

    public String getInfoPlaylist(int id) {
        return exigirPlaylist(id).informacoes();
    }

    public String getTodasPlaylists() {
        if (playlists.isEmpty()) {
            return "Não há playlists cadastradas.";
        }
        String todasPlaylists = "";
        for (Playlist playlist : playlists) {
            todasPlaylists += playlist.informacoes();
        }
        return todasPlaylists;
    }

    public String getTodasMusicasPlaylist(int idPlaylist) {
        return exigirPlaylist(idPlaylist).getTodasMusicas();
    }

    public boolean addMusicaPlaylist(int idPlaylist, int idMusica) {
        Playlist playlist = exigirPlaylist(idPlaylist);
        Musica musica = buscarMusica(idMusica);
        if (musica == null) {
            throw new IllegalArgumentException("Não existe música com o id " + idMusica + ".");
        }
        return playlist.adicionar(musica);
    }

    public boolean excluirMusicaPlaylist(int idPlaylist, int idMusica) {
        Playlist playlist = exigirPlaylist(idPlaylist);

        int posicao = playlist.posicaoDe(idMusica);
        if (posicao < 0) {
            return false;
        }
        return playlist.removerNaPosicao(posicao);
    }

    public boolean excluirPlaylist(int idPlaylist) {
        Playlist playlist = buscarPlaylist(idPlaylist);
        if (playlist == null) {
            return false;
        }
        return playlists.remove(playlist);
    }

    public Musica getMusicaPlaylist(int idPlaylist, int posicao) {
        return exigirPlaylist(idPlaylist).getNaPosicao(posicao);
    }

    public void tocarPlaylist(int id) {
        exigirPlaylist(id).reproduzirTudo();
    }

    // ------------------------------------------------------------------
    // Apoio
    // ------------------------------------------------------------------

    private Usuario exigirUsuario(int id) {
        Usuario usuario = buscarUsuario(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Não existe usuário com o id " + id + ".");
        }
        return usuario;
    }

    private Playlist exigirPlaylist(int id) {
        Playlist playlist = buscarPlaylist(id);
        if (playlist == null) {
            throw new IllegalArgumentException("Não existe playlist com o id " + id + ".");
        }
        return playlist;
    }

}
