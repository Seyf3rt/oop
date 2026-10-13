import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Cardápio
        System.out.println("=== Cardápio RangoJá ===");
        Prato feijoada = new Prato("Feijoada", 30.0, 'M');
        Bebida refrigerante = new Bebida("Refrigerante", 6.0, 350, false);
        Bebida chopp = new Bebida("Chopp", 12.0, 500, true);
        Sobremesa pudim = new Sobremesa("Pudim", 8.0, 150);

        ArrayList<ItemCardapio> cardapio = new ArrayList<>();
        cardapio.add(feijoada);
        cardapio.add(refrigerante);
        cardapio.add(chopp);
        cardapio.add(pudim);

        for (ItemCardapio item : cardapio) {
            System.out.println(item);
        }

        // 2. Clientes
        System.out.println("\n=== Clientes ===");
        Cliente ana = new Cliente("Ana Souza", "ana@furb.br", 20);
        Cliente beto = new Cliente("Beto Lima", "beto@furb.br", 17, ana);
        System.out.println(ana + " | cashback: R$ " + String.format("%.2f", ana.getSaldoCashback()));
        System.out.println(beto + " | indicado por: " + beto.getIndicadoPor().getNome());

        // 3. Pedido do Beto com quantidade lida do teclado
        System.out.println("\n=== Pedido do Beto ===");
        Pedido pedidoBeto = new Pedido(beto);
        boolean adicionado = false;
        while (!adicionado) {
            System.out.print("Quantas " + feijoada.getNome() + " o Beto quer? ");
            try {
                int quantidade = Integer.parseInt(sc.nextLine().trim());
                pedidoBeto.adicionarItem(feijoada, quantidade);
                adicionado = true;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println("Itens no pedido: " + pedidoBeto.getQuantidadeItens());

        // 4. Beto (menor de idade) tenta pedir chopp
        try {
            pedidoBeto.adicionarItem(chopp);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        try {
            pedidoBeto.finalizar();
            System.out.println("Pedido " + pedidoBeto.getNumero() + " finalizado. "
                    + pedidoBeto.gerarComprovante());
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // 5. Pedido da Ana: adicionar e finalizar no mesmo try (multi-catch)
        System.out.println("\n=== Pedido da Ana ===");
        Pedido pedidoAna = new Pedido(ana);
        try {
            pedidoAna.adicionarItem(feijoada);
            pedidoAna.adicionarItem(chopp);
            pedidoAna.adicionarItem(pudim);
            pedidoAna.finalizar();
            System.out.println("Pedido " + pedidoAna.getNumero() + " finalizado. "
                    + pedidoAna.gerarComprovante());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        try {
            pedidoAna.finalizar();
        } catch (IllegalStateException e) {
            System.out.println("Erro ao finalizar de novo: " + e.getMessage());
        }

        // 6. Ana tenta usar mais cashback do que tem
        System.out.println("\n=== Cashback da Ana ===");
        try {
            ana.usarCashback(1000);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("Saldo atual da Ana: R$ " + String.format("%.2f", ana.getSaldoCashback()));
        }

        // 7. Central de cobrança
        System.out.println("\n=== Relatório de cobranças ===");
        CentralCobranca central = new CentralCobranca();
        try {
            AssinaturaClube assinatura = new AssinaturaClube(ana, 19.90, 12);
            central.registrar(assinatura);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        if (pedidoBeto.isFinalizado()) {
            central.registrar(pedidoBeto);
        }
        if (pedidoAna.isFinalizado()) {
            central.registrar(pedidoAna);
        }
        System.out.println(central.gerarRelatorio());

        sc.close();
    }
}
