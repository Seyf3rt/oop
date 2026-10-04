public abstract class Plano {
    private String nome;
    private int maxDispositivos;
    boolean anuncio = false;
    private double precoMensal;

    public void setNome(String nome) {
        this.nome = nome;
    }

    abstract boolean temAnuncio();

    public String getNome() {
        return nome;
    }

    public void setMaxDispositivos(int quantidade) {
        this.maxDispositivos = quantidade;
    }


    abstract double calcularMensalidade();

    public void setPrecoMensal(double precoMensal) {
        if (precoMensal <= 0) {
            throw new IllegalArgumentException("Preco deve ser positivo");
        }
        this.precoMensal = precoMensal;
    }


    public double getPrecoMensal() {
        return precoMensal;
    }

    public String resumo() {
        return nome + ": R$ " + precoMensal
                + " por mes, " + maxDispositivos + " dispositivo(s)";
    }
}
