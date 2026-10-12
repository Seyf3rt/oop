public abstract class PlanoPago extends Plano {
    private double precoMensal;

    public double getPrecoMensal() {
        return precoMensal;
    }

    public void setPrecoMensal(double precoMensal) {
        if (precoMensal <= 0) {
            throw new IllegalArgumentException("Preco deve ser positivo");
        }
        this.precoMensal = precoMensal;
    }

    @Override
    public boolean temAnuncios() {
        return false;
    }

    

}
