public abstract class ItemCardapio {
    private String nome;
    private double precoBase;

    public ItemCardapio(String nome, double precoBase) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome está vazio");
        } else {
            this.nome = nome;
        }

        setPrecoBase(precoBase);
    }

    public String getNome() {
        return this.nome;
    }

    public double getPrecoBase() {
        return this.precoBase;
    }

    public void setPrecoBase(double precoBase) {
        validarPositivo(precoBase);
        this.precoBase = precoBase;
    }

    protected void validarPositivo(double valor) {
        if (valor < 0 || valor == 0) {
            throw new IllegalArgumentException("Valor inválido.");
        }
    }

    public abstract double calcularPreco();

    public final double calcularPrecoComDesconto(double percentual) {
        if (percentual <=0 || percentual > 100) {
            throw new IllegalArgumentException("Valor inválido.");
        }
        return calcularPreco()-((calcularPreco()*percentual)/100);
        //5 
    }

    @Override
    public String toString() {
        return getNome()+" - R$ " + String.format("%.2f", calcularPreco());
    }

}
