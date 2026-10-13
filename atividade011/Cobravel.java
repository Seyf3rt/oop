public interface Cobravel {
    public double calcularValorCobrar();
    default String gerarComprovante(){
        return "Valor cobrado: R$ "+ calcularValorCobrar();
    }
}
