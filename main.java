import java.util.Scanner;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    // ------------------ Catálogo de produtos ------------------
    static int[] idsProdutos;
    static String[] nomesProdutos;
    static double[] precosProdutos;
    static int[] estoquesProdutos;

    // ------------------ Venda atual ------------------
    static int[] vendaAtualIds = new int[0];
    static int[] vendaAtualQuantidades = new int[0];

    // ------------------ Histórico de vendas ------------------
    static int[] historicoIdsPedidos = new int[0];
    static double[] historicoValoresPedidos = new double[0];
    static int[][] historicoItensVendidos = new int[0][3]; // [ID Pedido, ID Produto, Quantidade]

    static boolean baseInicializada = false;
    static int proximoIdPedido = 1001;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MACKSHOP =====");
            System.out.println("1. Inicializar base");
            System.out.println("2. Exibir catálogo de produtos");
            System.out.println("3. Adicionar item à venda");
            System.out.println("4. Ver resumo da venda atual");
            System.out.println("5. Finalizar venda");
            System.out.println("6. Ver histórico de vendas");
            System.out.println("7. Buscar venda específica do histórico");
            System.out.println("8. (Admin) Repor estoque");
            System.out.println("9. (Admin) Relatório de estoque baixo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch(opcao) {
                case 1: inicializarBase(); break;
                case 2: exibirCatalogo(); break;
                case 3: adicionarItemVenda(); break;
                case 4: verResumoVendaAtual(); break;
                case 5: finalizarVenda(); break;
                case 6: verHistorico(); break;
                case 7: buscarVendaHistorico(); break;
                case 8: reporEstoque(); break;
                case 9: relatorioEstoqueBaixo(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!"); break;
            }
        } while(opcao != 0);
    }

    // ------------------ Métodos ------------------
    static void inicializarBase() {
        idsProdutos = new int[]{101, 203, 401};
        nomesProdutos = new String[]{"Mouse Gamer", "Teclado Mecânico", "Headset 7.1"};
        precosProdutos = new double[]{150.00, 350.00, 420.50};
        estoquesProdutos = new int[]{10, 5, 8};

        vendaAtualIds = new int[0];
        vendaAtualQuantidades = new int[0];

        historicoIdsPedidos = new int[0];
        historicoValoresPedidos = new double[0];
        historicoItensVendidos = new int[0][3];

        baseInicializada = true;
        System.out.println("Base inicializada com sucesso!");
    }

    static void exibirCatalogo() {
        if(!verificarBase()) return;

        System.out.println("\nCatálogo de Produtos:");
        System.out.println("ID | Produto | Preço | Estoque");
        for(int i=0; i<idsProdutos.length; i++) {
            if(estoquesProdutos[i] > 0) {
                System.out.printf("%d | %s | R$ %.2f | %d%n",
                        idsProdutos[i], nomesProdutos[i], precosProdutos[i], estoquesProdutos[i]);
            }
        }
    }

    static void adicionarItemVenda() {
        if(!verificarBase()) return;
        // Aqui você deve implementar a leitura do ID e quantidade, validação, e adicionar ao vetor da venda
        System.out.println("Funcionalidade ainda não implementada");
    }

    static void verResumoVendaAtual() {
        if(!verificarBase()) return;
        System.out.println("Funcionalidade ainda não implementada");
    }

    static void finalizarVenda() {
        if(!verificarBase()) return;
        System.out.println("Funcionalidade ainda não implementada");
    }

    static void verHistorico() {
        if(!verificarBase()) return;
        System.out.println("Funcionalidade ainda não implementada");
    }

    static void buscarVendaHistorico() {
        if(!verificarBase()) return;
        System.out.println("Funcionalidade ainda não implementada");
    }

    static void reporEstoque() {
        if(!verificarBase()) return;
        System.out.println("Funcionalidade ainda não implementada");
    }

    static void relatorioEstoqueBaixo() {
        if(!verificarBase()) return;
        System.out.println("Funcionalidade ainda não implementada");
    }

    // ------------------ Métodos auxiliares ------------------
    static boolean verificarBase() {
        if(!baseInicializada) {
            System.out.println("Erro: inicialize a base antes de usar esta função!");
            return false;
        }
        return true;
    }

    static void imprimirNotaFiscal(int idPedido, int[] ids, int[] quantidades) {
        // Aqui você pode implementar a impressão da nota fiscal formatada conforme o modelo enviado
    }
}
