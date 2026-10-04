public class PlanoFamilia extends PlanoPago {
    private int quantidadeMembros;

    public PlanoFamilia(double precoMensal, int quantidadeMembros) {
        super.setNome("Familia");
        super.setMaxDispositivos(6);
        setPrecoMensal(precoMensal);
        setQuantidadeMembros(quantidadeMembros);
    }

    public int getQuantidadeMembros() {
        return quantidadeMembros;
    }

    public void setQuantidadeMembros(int quantidadeMembros) {
        if (quantidadeMembros < 1 || quantidadeMembros > 6) {
            throw new IllegalArgumentException("Membros deve ser de 1 a 6");
        }
        this.quantidadeMembros = quantidadeMembros;
    }

    @Override
    public double calcularMensalidade() {
        return getPrecoMensal() + 4.90 * (quantidadeMembros - 1);
    }

}
