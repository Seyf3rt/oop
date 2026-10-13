public class Sobremesa extends ItemCardapio{
    private int pesoGramas;

    public Sobremesa(String nome, double preco, int pesoGramas) {
        super(nome, preco);

        validarPositivo(pesoGramas);
        this.pesoGramas = pesoGramas;
    }

    public int getPesoGramas() {
        return pesoGramas;
    }

    @Override 
    public double calcularPreco() {
        double valor = (double)pesoGramas/100;
            return valor*getPrecoBase();
    }

    @Override
    public String toString() {
        return super.toString()+ " ("+pesoGramas+" g)";
    }

    

    
}
