public class AssinaturaClube implements Cobravel{
    private double valorMensal;
    private int meses;
    private Cliente cliente;

    public AssinaturaClube(Cliente cliente, double valorMensal, int meses) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente vazio.");
        }
        if (valorMensal < 1) {
            throw new IllegalArgumentException("Valor mensal deve ser posítivo.");
        }
        if (meses < 1) {
            throw new IllegalArgumentException("Mínimo de um mês.");
        }

        this.cliente = cliente;
        this.valorMensal = valorMensal;
        this.meses = meses;

    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public int getMeses() {
        return meses;
    }
    @Override
    public double calcularValorCobrar() {
        if (meses >=12){
            return (valorMensal*meses)-(((valorMensal*meses)*15)/100);
        }
        return valorMensal*meses;
    }

}
