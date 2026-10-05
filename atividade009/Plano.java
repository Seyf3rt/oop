public abstract class Plano {
    private String nome;
    private int maxDispositivos;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setMaxDispositivos(int dispositivos) {
        this.maxDispositivos = dispositivos;
    }

    public int getMaxDispositivos() {
        return maxDispositivos;
    }

    public abstract double calcularMensalidade();

    public abstract boolean temAnuncios();

    public final String resumo() {
        return nome + ": R$ " + calcularMensalidade()
                + " por mes, " + maxDispositivos + " dispositivo(s)";
    }
}
