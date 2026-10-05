abstract class Conteudo {

    private static int contagem;
    private int id;
    private String titulo;
    private int duracaoSegundos;
    private int reproducoes;

    public Conteudo(String titulo, int duracaoSegundos) {
        setTitulo(titulo);
        setDuracaoSegundos(duracaoSegundos);
        contagem++;
        setId(contagem);
    }

    public abstract String getCredito();

    public static int getContagem() {
        return contagem;
    }

    public static void setContagem(int n_contagem) {
        contagem = n_contagem;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título inválido: não pode ser nulo nem vazio.");
        }
        this.titulo = titulo;
    }

    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        if (duracaoSegundos <= 0) {
            throw new IllegalArgumentException(
                    "Duração inválida: " + duracaoSegundos + ". A duração deve ser maior que zero.");
        }
        this.duracaoSegundos = duracaoSegundos;
    }

    public int getReproducoes() {
        return reproducoes;
    }

    public String getDuracaoFormatada() {
        int minutos = duracaoSegundos / 60;
        int segundos = duracaoSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    public final void reproduzir() {    
        reproducoes++;
        System.out.println("Reproduzindo: " + toString() + " | reproduções: " + reproducoes + " | "+getCredito());
    }

    @Override
    public String toString() {
        return "[" + getId() + "] " + titulo + " (" + duracaoSegundos + "s)";
    }
}
