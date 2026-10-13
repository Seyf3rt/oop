import java.util.ArrayList;

public class CentralCobranca {
    private ArrayList<Cobravel> cobrancas = new ArrayList<>();

    public void registrar(Cobravel cobranca) {
        if (cobranca == null) {
            throw new IllegalArgumentException("cobrança vazia.");
        }

        cobrancas.add(cobranca);
    }

    public double calcularTotalAReceber() {
        double total = 0;
        for (Cobravel c : cobrancas) {
            total += c.calcularValorCobrar();
        }
        return total;
    }

    public int getQuantidadeCobrancas() {
        return cobrancas.size();
    }

    public String gerarRelatorio() {
        String relatorio = "";
        for (Cobravel c : cobrancas) {
            relatorio += c.gerarComprovante() + "\n";
        }

        return relatorio + "Total a receber: R$ " + String.format("%.2f", calcularTotalAReceber());
    }

}
