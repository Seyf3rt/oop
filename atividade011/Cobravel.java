public interface Cobravel {
    public double calcularValorCobrar();
    default String gerarComprovante(){
        return "Valor a cobrar: R$ " + String.format("%.2f", calcularValorCobrar());
    }
}
