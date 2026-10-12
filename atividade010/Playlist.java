import java.util.ArrayList;

class Playlist {
    private Usuario dono;
    private String titulo;
    private ArrayList<Conteudo> conteudos = new ArrayList<>();
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
        return conteudos.size();
    }

    /**
     * Adiciona a música no fim da playlist. A lista cresce sozinha, então não existe
     * mais "playlist cheia": o retorno é sempre true (mantido pra não quebrar quem chama).
     */
    public boolean adicionar(Conteudo conteudo) {
        if (conteudo == null) {
            throw new IllegalArgumentException("Não é possível adicionar um conteúdo nula à playlist.");
        }
        return conteudos.add(conteudo);
    }

    public Conteudo getNaPosicao(int indice) {
        if (indice < 0 || indice >= conteudos.size()) {
            throw new IndexOutOfBoundsException(
                    "Posição " + indice + " inválida. A playlist tem " + conteudos.size() + " conteúdos (s).");
        }
        return conteudos.get(indice);
    }

    public boolean removerNaPosicao(int indice) {
        if (indice < 0 || indice >= conteudos.size()) {
            throw new IndexOutOfBoundsException(
                    "Posição " + indice + " inválida. A playlist tem " + conteudos.size() + " conteúdo(s).");
        }
        conteudos.remove(indice); // o ArrayList já desloca as seguintes pra trás
        return true;
    }

    public int posicaoDe(int idConteudo) {
        for (int i = 0; i < conteudos.size(); i++) {
            if (conteudos.get(i).getId() == idConteudo) {
                return i;
            }
        }
        return -1;
    }

    public int getDuracaoSegundos() {
        int total = 0;
        for (Conteudo conteudo : conteudos) {
            total += conteudo.getDuracaoSegundos();
        }
        return total;
    }

    public String getTodasMusicas() {
        if (conteudos.isEmpty()) {
            return "A playlist está vazia.";
        }
        String todosConteudos = "";
        for (Conteudo conteudo : conteudos) {
            todosConteudos += "\n" + conteudo;
        }
        return todosConteudos;
    }

    public String getDuracaoFormatada() {
        int minutos = getDuracaoSegundos() / 60;
        int segundos = getDuracaoSegundos() % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    public void reproduzirTudo() {
        for (Conteudo conteudo : conteudos) {
            conteudo.reproduzir();
        }
    }

    public String informacoes() {
        return "\nTítulo: " + titulo + " | Dono: " + dono.getNome() + " | Músicas: " + conteudos.size()
                + " | Duração total: " + getDuracaoFormatada() + " | Id: " + id;
    }

}
