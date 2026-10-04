public class PlanoGratuito extends Plano {

    public PlanoGratuito() {
        super.setNome("Gratuito");
        super.setMaxDispositivos(1);
    }

    @Override
    public boolean temAnuncio(){
        return false;
    }

    @Override
    public double calcularMensalidade() {
        return 0.0;
    }
}
