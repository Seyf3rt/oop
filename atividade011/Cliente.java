public class Cliente {
    private String nome;
    private String email;
    private int idade;
    private double saldoCashback = 0;
    private Cliente indicadoPor;

    public Cliente(String nome, String email, int idade) {

        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome está vazio");
        } else {
            this.nome = nome;
        }

        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email está vazio");
        } else if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        } else {
            this.email = email;
        }
        setIdade(idade);

    }

    public Cliente(String nome, String email, int idade, Cliente indicadoPor) {
        super();
        if (indicadoPor == null) {
            throw new IllegalArgumentException("Indicado por ninguem");
        } else {
            this.indicadoPor = indicadoPor;
        }

        indicadoPor.creditarCashback(10);

    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 120) {
            throw new IllegalArgumentException("Idade inválida");
        } else {
            this.idade = idade;
        }

    }

    public double getSaldoCashback() {
        return saldoCashback;
    }

    public Cliente getIndicadoPor() {
        return indicadoPor;
    }

    public boolean isMaiorDeIdade() {
        if (idade >= 18) {
            return true;
        }
        return false;

    }

    public void creditarCashback(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que 0!");
        } else {
            saldoCashback += valor;
        }

    }

    public void usarCashback(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que 0!");
        } else if (valor > saldoCashback) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente: disponível R$" + getSaldoCashback() + "\n Solicitado: R$" + valor);
        } else {
            saldoCashback -= valor;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cliente)){
            return false;
        } 
        Cliente outro = (Cliente) obj;

        if (outro.getEmail().equalsIgnoreCase(getEmail())){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getNome()+" "+getEmail();
    }

}
