public class Bebida extends ItemCardapio{
    private int volumeMl;
    private boolean alcoolica;

    public Bebida(String nome, double precoBase, int volumeML, boolean alcoolica) {
        super(nome, precoBase);
        super.validarPositivo(volumeML);
        this.volumeMl = volumeML;
        this.alcoolica = alcoolica;
    }

    public int getVolumeML(){
        return volumeMl;
    }

    public boolean isAlcoolica(){
        return alcoolica;
    }

    @Override
    public double calcularPreco(){
        if (alcoolica){
            return getPrecoBase()+ 3;
        }

        return getPrecoBase();
    }

    @Override 
    public String toString(){
        if (alcoolica){
            return super.toString()+"("+getVolumeML()+" ml) [alcoólica]";
        }
        return super.toString()+"("+getVolumeML()+" ml)";

    }

    
}
