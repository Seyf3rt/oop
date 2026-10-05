public class PlanoIndividual extends PlanoPago {
    
    public PlanoIndividual(double precoMensal) {
        super.setNome("Individual");
        super.setMaxDispositivos(1);
        setPrecoMensal(precoMensal);
    }

    @Override 
    public double calcularMensalidade() {
        return getPrecoMensal();
    }

}