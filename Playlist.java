import java.util.ArrayList;

class Playlist {
    private Usuario dono;
    private String titulo;
    private ArrayList<Musica> musicas = new ArrayList<>();
    private int id;
    private static int contagem; // é de playlist

    public Playlist(String titulo, Usuario dono) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da playlist inválido: não pode ser nulo nem vazio.");
        }
        if (dono == null) {
            throw new IllegalArgumentException("A playlist precisa de um dono (não pode ser nulo).");
        }

        this.dono = dono;
        this.titulo = titulo;
        contagem++;
        id = contagem;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Usuario getDono() {
        return dono;
    }

    public static int getContagem() {
        return contagem;
    }

    /** Quantas músicas a playlist tem. Quem responde é o size() da lista. */
    public int getQuantidade() {
        return musicas.size();
    }

    /**
     * Adiciona a música no fim da playlist. A lista cresce sozinha, então não existe
     * mais "playlist cheia": o retorno é sempre true (mantido pra não quebrar quem chama).
     */
    public boolean adicionar(Musica musica) {
        if (musica == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma música nula à playlist.");
        }
        return musicas.add(musica);
    }

    public Musica getNaPosicao(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            throw new IndexOutOfBoundsException(
                    "Posição " + indice + " inválida. A playlist tem " + musicas.size() + " música(s).");
        }
        return musicas.get(indice);
    }

    public boolean removerNaPosicao(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            throw new IndexOutOfBoundsException(
                    "Posição " + indice + " inválida. A playlist tem " + musicas.size() + " música(s).");
        }
        musicas.remove(indice); // o ArrayList já desloca as seguintes pra trás
        return true;
    }

    /** Posição da música com esse id na playlist, ou -1 se ela não está aqui. */
    public int posicaoDe(int idMusica) {
        for (int i = 0; i < musicas.size(); i++) {
            if (musicas.get(i).getId() == idMusica) {
                return i;
            }
        }
        return -1;
    }

    public int getDuracaoSegundos() {
        int total = 0;
        for (Musica musica : musicas) {
            total += musica.getDuracaoSegundos();
        }
        return total;
    }

    public String getTodasMusicas() {
        if (musicas.isEmpty()) {
            return "A playlist está vazia.";
        }
        String todasMusicas = "";
        for (Musica musica : musicas) {
            todasMusicas += "\n" + musica;
        }
        return todasMusicas;
    }

    public String getDuracaoFormatada() {
        int minutos = getDuracaoSegundos() / 60;
        int segundos = getDuracaoSegundos() % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    public void reproduzirTudo() {
        for (Musica musica : musicas) {
            musica.reproduzir();
        }
    }

    public String informacoes() {
        return "\nTítulo: " + titulo + " | Dono: " + dono.getNome() + " | Músicas: " + musicas.size()
                + " | Duração total: " + getDuracaoFormatada() + " | Id: " + id;
    }

}
