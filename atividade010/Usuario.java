import java.util.ArrayList;

class Usuario {

    private int id;
    private String nome;
    private String email;
    private static int contagem;
    private Plano plano;



    // Associação reflexiva "segue": os usuários que ESTE usuário segue (papel:
    // seguindo).
    // O papel inverso (seguidores) é derivado pela Plataforma percorrendo os
    // cadastrados.
    private ArrayList<Usuario> seguindo = new ArrayList<>();

    public Usuario(String nome, String email) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido: não pode ser nulo nem vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail inválido: não pode ser nulo nem vazio.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido: \"" + email + "\" não contém @.");
        }


        this.plano = new PlanoGratuito();
        this.nome = nome;
        this.email = email;
        contagem++;
        id = contagem;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public static int getContagem() {
        return contagem;
    }

    public boolean assinar (Plano novoPlano){
        if (novoPlano == null){
            throw new IllegalArgumentException("Plano invalido.");
        } else {
            this.plano = novoPlano;
            return true;
        }
        
    }

    public Plano getPlano(){
        return plano;
    }

    // ------------------------------------------------------------------
    // Associação reflexiva: seguir / deixar de seguir
    // ------------------------------------------------------------------

    /**
     * Passa a seguir outro usuário.
     *
     * @throws IllegalArgumentException se outro for nulo ou for este próprio
     *                                  usuário
     * @throws IllegalStateException    se este usuário já segue outro
     */
    public void seguir(Usuario outro) {
        if (outro == null) {
            throw new IllegalArgumentException("Não é possível seguir um usuário nulo.");
        }
        if (outro == this) {
            throw new IllegalArgumentException("Um usuário não pode seguir a si mesmo.");
        }
        if (seguindo.contains(outro)) {
            throw new IllegalStateException(nome + " já segue " + outro.getNome() + ".");
        }
        seguindo.add(outro);
    }

    /**
     * Deixa de seguir outro usuário.
     *
     * @throws IllegalArgumentException se outro for nulo
     * @throws IllegalStateException    se este usuário não segue outro
     */
    public void deixarDeSeguir(Usuario outro) {
        if (outro == null) {
            throw new IllegalArgumentException("Não é possível deixar de seguir um usuário nulo.");
        }
        if (!seguindo.remove(outro)) {
            throw new IllegalStateException(nome + " não segue " + outro.getNome() + ".");
        }
    }

    public int getQuantidadeSeguindo() {
        return seguindo.size();
    }

    public boolean segue(Usuario outro) {
        return seguindo.contains(outro);
    }

    /**
     * Cópia da lista de quem este usuário segue (a lista interna não sai daqui).
     */
    public ArrayList<Usuario> getSeguindo() {
        return new ArrayList<>(seguindo);
    }

    public String informacoes() {
        return "===================================\nNome: " + nome + " \nEmail: " + email + "\nId: " + id
                + "\nSeguindo: " + getQuantidadeSeguindo() + " usuário(s)"
                + "\n===================================";
    }

}
