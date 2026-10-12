public class Anuncio implements Cobravel, Faturavel,Reproduzivel,Cronometravel {
    private String anunciante;
    private String titulo;
    private int duracaoSegundos;
    private double valorPorMilImpressoes;
    private int impressoes;

    public Anuncio(String anunciante, String titulo, int duracaoSegundos, double valorPorMilImpressoes) {
        this.anunciante = anunciante;
        this.titulo = titulo;
        if (duracaoSegundos <= 0) {
            throw new IllegalArgumentException("A duração deve ser de ao menos 1 segundo.");
        } else {
            this.duracaoSegundos = duracaoSegundos;
        }

        if (valorPorMilImpressoes <= 0) {
            throw new IllegalArgumentException("O valor por mil impressoes deve ser de ao menos 1 real.");
        } else {
            this.valorPorMilImpressoes = valorPorMilImpressoes;
        }

    }

    @Override
    public void reproduzir() {
        impressoes++;
        System.out.println("[Anuncio] " + titulo + " - oferecimento de " + anunciante);
    }

    public double calcularValorVeiculacao() {
        return (impressoes / 1000.0) * valorPorMilImpressoes;
    }

    public String getAnunciante() {
        return anunciante;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getImpressoes() {
        return impressoes;
    }

    @Override 
    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }

    public double getValorPorMilImpressoes() {
        return valorPorMilImpressoes;
    }

    public void setValorPorMilImpressoes(int valorPorMilImpressoes) {
        this.valorPorMilImpressoes = valorPorMilImpressoes;
    }

    @Override
    public double calcularValorMensal() {
        return calcularValorVeiculacao();
    }

    @Override
    public double calcularMensalidade() {
        return calcularValorVeiculacao();

    }
}
