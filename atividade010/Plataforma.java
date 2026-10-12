import java.util.ArrayList;

class Plataforma {
    // Acervo da plataforma. Antes eram arrays de tamanho fixo (500/500/500)
    // posicionados
    // pelos contadores static das outras classes; agora são listas que crescem
    // sozinhas
    // e a busca é sempre pelo id do objeto, nunca pela posição.

    private ArrayList<Conteudo> conteudos = new ArrayList<>();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private ArrayList<Anuncio> anuncios = new ArrayList<>();

    // ------------------------------------------------------------------
    // Músicas
    // ------------------------------------------------------------------

    public double calcularReceitaMensal() {

        ArrayList<Faturavel> faturaveis = new ArrayList<>();
        faturaveis.addAll(anuncios);

        for (Usuario u : usuarios) {
            faturaveis.add(u.getPlano());
        }

        double total = 0;
        for (Faturavel f : faturaveis) {
            total += f.calcularMensalidade();
        }

        return total;
    }

    public boolean cadastrarMusica(Musica musica) {
        if (musica == null || conteudos.contains(musica)) {
            return false;
        }
        return conteudos.add(musica);
    }

    public int getTotalMusicas() {
        int quantidade = 0;
        for (Conteudo conteudo : conteudos) {
            if (conteudo instanceof Musica) {
                quantidade++;
            }

        }

        return quantidade;
    }

    public String getTodosConteudos() {
        if (conteudos.isEmpty()) {
            return "Não há músicas cadastradas.";
        }
        String todasMusicas = "";
        for (Conteudo conteudo : conteudos) {
            if (conteudo instanceof Musica) {
                todasMusicas += "\n" + conteudo;
            }
        }
        return todasMusicas;
    }

    public Musica buscarMusica(int id) {
        for (Conteudo conteudo : conteudos) {
            if (conteudo instanceof Musica) {
                if (conteudo.getId() == id) {
                    return (Musica) conteudo; // downcasting na pratica
                }

            }

        }
        return null;
    }

    public Musica buscarMusica(String titulo) {
        if (titulo == null) {
            return null;
        }

        for (Conteudo conteudo : conteudos) {

            if (conteudo instanceof Musica) {
                if (conteudo.getTitulo().equalsIgnoreCase(titulo)) {
                    return (Musica) conteudo;
                }
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
        return conteudos.remove(musica);
    }

    // ------------------------------------------------------------------
    // Podcasts
    // ------------------------------------------------------------------

    public String podcastsPorApresentador (String apresentador){
        String todosPodcasts = "";
        for (Conteudo conteudo : conteudos) {
            if (conteudo instanceof Podcast) {
                if(((Podcast) conteudo).getApresentador().toLowerCase().equals(apresentador.toLowerCase())){
                    todosPodcasts += "\n" + conteudo;
                }
                
            }
        }

        if (todosPodcasts.equals("")){
            todosPodcasts = "Não foi encontrado podcast's deste apresentador";
        }
        return todosPodcasts;
    }

    public boolean cadastrarPodcast(Podcast podcast) {
        if (podcast == null || conteudos.contains(podcast)) {
            return false;
        }
        return conteudos.add(podcast);
    }

    public String getTodosPodcasts() {
        if (conteudos.isEmpty()) {
            return "Não há podcasts cadastrados.";
        }
        String todosPodcasts = "";
        for (Conteudo conteudo : conteudos) {
            if (conteudo instanceof Podcast) {
                todosPodcasts += "\n" + conteudo;
            }
        }
        return todosPodcasts;
    }

    public Podcast buscarPodcast(int id) {
        for (Conteudo conteudo : conteudos) {
            if (conteudo instanceof Podcast) {
                if (conteudo.getId() == id) {
                    return (Podcast) conteudo;
                }
            }

        }
        return null;
    }

    public boolean excluirPodcast(int idPodcast) {
        Podcast podcast = buscarPodcast(idPodcast);
        if (podcast == null) {
            return false;
        }
        return conteudos.remove(podcast);
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

    public boolean addPodcastPlaylist(int idPlaylist, int idPodcast) {
        Playlist playlist = exigirPlaylist(idPlaylist);
        Podcast podcast = buscarPodcast(idPodcast);
        if (podcast == null) {
            throw new IllegalArgumentException("Não existe música com o id " + idPodcast + ".");
        }
        return playlist.adicionar(podcast);
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
        return (Musica) exigirPlaylist(idPlaylist).getNaPosicao(posicao);
    }

    public void tocarPlaylist(int id) {
        exigirPlaylist(id).reproduzirTudo();
    }

    public void tocarPlaylist(Usuario ouvinte, Playlist playlist) {
        ArrayList<Reproduzivel> fila = new ArrayList<>();

        // verificar o plano do user
        if (ouvinte.getPlano().temAnuncios() && !anuncios.isEmpty()) {
            int anuncioCont = 0;
            int anuncio = 0;

            for (int i = 0; i < playlist.getQuantidade(); i++) {
                fila.add(playlist.getNaPosicao(i));
                anuncioCont++;
                if (anuncioCont == 2) {
                    fila.add(anuncios.get(anuncio));
                    anuncioCont = 0;
                    anuncio++;
                    if (anuncio == anuncios.size()) {
                        anuncio = 0;
                    }

                }
            }

        } else {
            for (int i = 0; i < playlist.getQuantidade(); i++) {
                fila.add(playlist.getNaPosicao(i));
            }
        }

        for (Reproduzivel item : fila) {
            System.out.print(item.getDuracaoFormatada() + " | ");
            item.reproduzir();
        }

    }

    // ------------------------------------------------------------------
    // Anúncios
    // ------------------------------------------------------------------

    public boolean cadastrarAnuncio(Anuncio anuncio) {
        if (anuncio == null || anuncios.contains(anuncio)) {
            return false;
        }
        return anuncios.add(anuncio);
    }

    public int getTotalAnuncios() {
        return anuncios.size();
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

    public void listarEpisodiosDe(String apresentador) {
        for (Conteudo conteudo : conteudos) {
            if (conteudo instanceof Podcast) {
                if (((Podcast) conteudo).getApresentador().equals(apresentador)) {
                    System.out.println(conteudo.getCredito());
                }
            }
        }
    }

}
