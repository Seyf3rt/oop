import java.util.ArrayList;

public class Pedido implements Cobravel{
    public static double TAXA_ENTREGA = 7.0;
    public static double FRETE_GRATIS = 100.0;
    private static int proximoNumero = 1;
    private int numero;
    private boolean finalizado;
    private Cliente cliente;
    private ArrayList<ItemCardapio> itens = new ArrayList<>();

    public Pedido(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente vazio");
        }
        this.cliente = cliente;

        numero = proximoNumero++;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void adicionarItem(ItemCardapio item) {
        if (finalizado) {
            throw new IllegalStateException("Pedido já foi finalizado");
        } else if (item == null) {
            throw new IllegalArgumentException("Item vazio.");
        } else if (item instanceof Bebida && (!cliente.isMaiorDeIdade()) && (((Bebida) item).isAlcoolica())) {
            throw new IllegalArgumentException("Menor de idade não pode consumir bebida alcoolica!");
        } else {
            itens.add(item);
        }

    }

    public void adicionarItem(ItemCardapio item, int quantidade) {
        if (quantidade <1){
            throw new IllegalArgumentException("Quantidade inválida");
        }
        if (finalizado) {
            throw new IllegalStateException("Pedido já foi finalizado");
        } else if (item == null) {
            throw new IllegalArgumentException("Item vazio.");
        } else if (item instanceof Bebida && (!cliente.isMaiorDeIdade()) && (((Bebida) item).isAlcoolica())) {
            throw new IllegalArgumentException("Menor de idade não pode consumir bebida alcoolica!");
        } else {
            for (int i = 0; i< quantidade;i++){
                itens.add(item);
            }
            
        }
        
    }

    public int getQuantidadeItens() {
        return itens.size();
    }

    public double calcularSubTotal() {
        double total = 0;
        for (int i = 0; i < itens.size();i++){
            ItemCardapio item = itens.get(i);
            total += item.calcularPreco();
        }
        return total;

    }

    public double calcularTaxaEntrega() {
        if (calcularSubTotal() >= 100){
            return 0;
        }
        return TAXA_ENTREGA;
    }
    @Override
    public double calcularValorCobrar() {
        return calcularSubTotal()+calcularTaxaEntrega();
    }

    public void finalizar() {
        if (finalizado) {
            throw new IllegalStateException("Pedido já foi finalizado");
        }
        if (itens.isEmpty()){
            throw new IllegalStateException("Não há produtos selecionados.");
        }

        cliente.creditarCashback(calcularSubTotal()*0.05);
        finalizado = true;
    }

    public static int getTotalPedidosCriados() {
        return proximoNumero - 1;
    }

}
