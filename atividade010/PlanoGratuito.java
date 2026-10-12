public final class PlanoGratuito extends Plano {
    public PlanoGratuito() {
        super.setNome("Gratuito");
        super.setMaxDispositivos(1);
    }

    @Override 
    public final boolean temAnuncios() {
        return true;
    }

    @Override
    public double calcularMensalidade() {
        return 0.0;
    }

}