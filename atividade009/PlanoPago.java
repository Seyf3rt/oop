public abstract class PlanoPago extends Plano {

    @Override
    public double calcularMensalidade() {
        return getPrecoMensal();
    }

    @Override
    public boolean temAnuncio(){
        return true;
    }

}
