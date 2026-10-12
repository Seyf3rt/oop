import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe espelho de Usuario.
 *
 * Planos cobertos (ver PLANOS_DE_TESTE.md):
 *   PL09 - Validar construtor de Usuario com dados inválidos
 *   PL08 - Contadores de id (bônus) - casos 5 e 6
 *   PL12 - Associação reflexiva: seguir / deixar de seguir
 *
 * Observação: o contador de id de Usuario é static e não é zerado entre os testes,
 * por isso os casos de id comparam ids relativos, nunca valores absolutos.
 */
public class UsuarioTest {

    private Usuario lucas;

    @BeforeEach
    public void montarCenario() {
        lucas = new Usuario("Lucas", "lucas@sonora.com");
    }

    // ------------------------------------------------------------------
    // PL09 - Validar construtor de Usuario com dados inválidos
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Nome vazio deve ser rejeitado")
    public void pl09Caso1_nomeVazioDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("", "lucas@sonora.com"));
    }

    @Test
    @DisplayName("Nome nulo deve ser rejeitado")
    public void pl09Caso2_nomeNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario(null, "lucas@sonora.com"));
    }

    @Test
    @DisplayName("E-mail vazio deve ser rejeitado")
    public void pl09Caso3_emailVazioDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Lucas", ""));
    }

    @Test
    @DisplayName("E-mail nulo deve ser rejeitado")
    public void pl09Caso4_emailNuloDeveSerRejeitado() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("Lucas", null));
    }

    @Test
    @DisplayName("E-mail sem @ deve ser rejeitado")
    public void pl09Caso5_emailSemArrobaDeveSerRejeitado() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> new Usuario("Lucas", "lucas.sonora.com"));

        // A mensagem precisa descrever o erro, não só dizer "erro".
        assertNotNull(erro.getMessage());
        assertFalse(erro.getMessage().trim().isEmpty());
    }

    @Test
    @DisplayName("Dados válidos criam o usuário")
    public void pl09Caso6_dadosValidosCriamOUsuario() {
        assertNotNull(lucas);
        assertTrue(lucas.getId() > 0);
        assertEquals("Lucas", lucas.getNome());
        assertEquals("lucas@sonora.com", lucas.getEmail());
    }

    // ------------------------------------------------------------------
    // PL08 (bônus) - Contadores de id - casos 5 e 6
    // (casos 1 a 4, sobre os ids de Música, estão em MusicaTest)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Dois usuários criados em sequência têm ids consecutivos")
    public void pl08Caso5_doisUsuariosEmSequenciaTemIdsConsecutivos() {
        Usuario primeiro = new Usuario("Ana", "ana@sonora.com");
        Usuario segundo = new Usuario("Bruno", "bruno@sonora.com");

        assertEquals(primeiro.getId() + 1, segundo.getId());
    }

    @Test
    @DisplayName("Criar uma música entre dois usuários não interfere nos ids de Usuário")
    public void pl08Caso6_musicaNoMeioNaoInterfereNosIdsDeUsuario() {
        Usuario antes = new Usuario("Ana", "ana@sonora.com");
        Musica musica = new Musica("Bohemian Rhapsody", 355, "Queen", "A Night at the Opera");
        Usuario depois = new Usuario("Bruno", "bruno@sonora.com");

        assertNotNull(musica);
        assertEquals(antes.getId() + 1, depois.getId());
    }

    // ------------------------------------------------------------------
    // PL12 - Associação reflexiva: seguir / deixar de seguir
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Usuário recém-criado não segue ninguém")
    public void pl12Caso1_usuarioRecemCriadoNaoSegueNinguem() {
        assertEquals(0, lucas.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Seguir outro usuário sobe a quantidade para um")
    public void pl12Caso2_seguirOutroUsuarioSobeAQuantidade() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");

        lucas.seguir(ana);

        assertEquals(1, lucas.getQuantidadeSeguindo());
        assertTrue(lucas.segue(ana));
    }

    @Test
    @DisplayName("Seguir é de mão única: quem é seguido não passa a seguir de volta")
    public void pl12Caso3_seguirEDeMaoUnica() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");

        lucas.seguir(ana);

        assertTrue(lucas.segue(ana));
        assertFalse(ana.segue(lucas));
        assertEquals(0, ana.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Seguir vários usuários acumula na lista")
    public void pl12Caso4_seguirVariosUsuariosAcumula() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");
        Usuario bruno = new Usuario("Bruno", "bruno@sonora.com");

        lucas.seguir(ana);
        lucas.seguir(bruno);

        assertEquals(2, lucas.getQuantidadeSeguindo());
        assertTrue(lucas.segue(ana));
        assertTrue(lucas.segue(bruno));
    }

    @Test
    @DisplayName("Seguir a si mesmo lança IllegalArgumentException e nada é adicionado")
    public void pl12Caso5_seguirASiMesmoLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> lucas.seguir(lucas));
        assertEquals(0, lucas.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Seguir o mesmo usuário duas vezes lança IllegalStateException e não duplica")
    public void pl12Caso6_seguirDuasVezesOMesmoUsuarioLancaExcecao() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");
        lucas.seguir(ana);

        assertThrows(IllegalStateException.class, () -> lucas.seguir(ana));
        assertEquals(1, lucas.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Seguir usuário nulo lança IllegalArgumentException")
    public void pl12Caso7_seguirUsuarioNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> lucas.seguir(null));
        assertEquals(0, lucas.getQuantidadeSeguindo());
    }

    @Test
    @DisplayName("Deixar de seguir remove o usuário e a quantidade cai")
    public void pl12Caso8_deixarDeSeguirRemoveOUsuario() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");
        Usuario bruno = new Usuario("Bruno", "bruno@sonora.com");
        lucas.seguir(ana);
        lucas.seguir(bruno);

        lucas.deixarDeSeguir(ana);

        assertEquals(1, lucas.getQuantidadeSeguindo());
        assertFalse(lucas.segue(ana));
        assertTrue(lucas.segue(bruno));
    }

    @Test
    @DisplayName("Deixar de seguir quem não é seguido lança IllegalStateException")
    public void pl12Caso9_deixarDeSeguirQuemNaoESeguidoLancaExcecao() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");

        assertThrows(IllegalStateException.class, () -> lucas.deixarDeSeguir(ana));
    }

    @Test
    @DisplayName("Deixar de seguir usuário nulo lança IllegalArgumentException")
    public void pl12Caso10_deixarDeSeguirUsuarioNuloLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> lucas.deixarDeSeguir(null));
    }

    @Test
    @DisplayName("A lista devolvida por getSeguindo é uma cópia: mexer nela não afeta o usuário")
    public void pl12Caso11_getSeguindoDevolveCopia() {
        Usuario ana = new Usuario("Ana", "ana@sonora.com");
        lucas.seguir(ana);

        java.util.ArrayList<Usuario> copia = lucas.getSeguindo();
        copia.clear();

        assertEquals(1, lucas.getQuantidadeSeguindo());
        assertTrue(lucas.segue(ana));
    }
}
