public class Prato extends ItemCardapio {
    private char tamanho;

    public Prato(String nome, double precoBase, char tamanho) {
        super(nome, precoBase);
        if (tamanho != 'P' && tamanho != 'M' && tamanho != 'G') {
            throw new IllegalArgumentException("Tamanho inválido!");
        } else {
            this.tamanho = tamanho;
        }

    }

    public char getTamanho() {
        return tamanho;
    }

    @Override
    public double calcularPreco() {
        switch (tamanho) {
            case 'P':
                return getPrecoBase();
            case 'M':
                return getPrecoBase() + (getPrecoBase() * 0.3);
            case 'G':
                return getPrecoBase() + (getPrecoBase() * 0.6);
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (tamanho "+getTamanho()+").";
    }

}
