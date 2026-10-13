public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(double saldo, double valorSolicitado) {
        super(String.format("Saldo insuficiente: disponível R$ %.2f, solicitado R$ %.2f",
                saldo, valorSolicitado));
    }

}
