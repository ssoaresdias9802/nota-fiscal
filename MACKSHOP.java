import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MACKSHOP {

    // ===== Variáveis globais =====
    static int[] idsProdutos = new int[10];
    static String[] nomesProdutos = new String[10];
    static double[] precoProdutos = new double[10];
    static int[] estoquesProdutos = new int[10];

    static int[] vendaAtualIds = new int[0];
    static int[] vendaAtualQuantidades = new int[0];

    static int[] historicoIdsPedidos = new int[0];
    static double[] historicoValoresPedidos = new double[0];
    static int[][] historicoItensVendidos = new int[0][3];

    static int proximoIdPedido = 1; // para gerar IDs automáticos de pedidos

    public static void main(String[] args) {
        exibirMenu();
    }

    // ===== Menu principal =====
    public static void exibirMenu() {
        Scanner entrada = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=====MACKSHOP=====");
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
            opcao = entrada.nextInt();

            switch (opcao) {
                case 1 : 
                    inicializarBase();
                    break
                case 2 :
                    exibirCatalogo();
                    break;
                case 3 : 
                    adicionarItemVenda();
                    break
                case 4 :
                    verResumoVendaAtual();
                    break
                case 5 :
                     finalizarVenda();
                     break;
                case 6 :
                     verHistoricoVendas();
                case 7 :
                     buscarVenda();
                     break;
                case 8 :
                     reporEstoque();
                     break;
                case 9 :
                     relatorioEstoqueBaixo();
                     break
                case 0 :
                     System.out.println("Saindo...");
                    break;
                default :
                     System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
        entrada.close();
    }

    // ===== Inicialização da base =====
    public static void inicializarBase() {
        idsProdutos[0] = 101; nomesProdutos[0] = "Mouse Gamer"; precoProdutos[0] = 150.00; estoquesProdutos[0] = 100;
        idsProdutos[1] = 203; nomesProdutos[1] = "Teclado Mecânico"; precoProdutos[1] = 350.00; estoquesProdutos[1] = 50;
        idsProdutos[2] = 401; nomesProdutos[2] = "Headset 7.1"; precoProdutos[2] = 420.50; estoquesProdutos[2] = 75;
        System.out.println("Base inicializada com sucesso.");
    }

    // ===== Exibir catálogo =====
    public static void exibirCatalogo() {
        System.out.println("Catálogo de Produtos:");
        for (int i = 0; i < idsProdutos.length && idsProdutos[i] != 0; i++) {
            if (estoquesProdutos[i] > 0) {
                System.out.printf("ID: %d | Produto: %s | Preço: R$ %.2f | Estoque: %d\n",
                        idsProdutos[i], nomesProdutos[i], precoProdutos[i], estoquesProdutos[i]);
            } else {
                System.out.printf("ID: %d | Produto: %s | Preço: R$ %.2f | Estoque: Esgotado\n",
                        idsProdutos[i], nomesProdutos[i], precoProdutos[i]);
            }
        }
    }

    // ===== Adicionar item à venda =====
    public static void adicionarItemVenda() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do produto que deseja adicionar à venda: ");
        int idProduto = sc.nextInt();

        int pos = -1;
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] == idProduto) {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            System.out.println("ID de produto inválido.");
            return;
        }

        System.out.print("Digite a quantidade do produto: ");
        int quantidade = sc.nextInt();

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida. Deve ser maior que zero.");
            return;
        }

        if (quantidade > estoquesProdutos[pos]) {
            System.out.println("Quantidade indisponível em estoque.");
            return;
        }

        // Adicionar à venda atual (expandindo arrays)
        vendaAtualIds = java.util.Arrays.copyOf(vendaAtualIds, vendaAtualIds.length + 1);
        vendaAtualQuantidades = java.util.Arrays.copyOf(vendaAtualQuantidades, vendaAtualQuantidades.length + 1);
        vendaAtualIds[vendaAtualIds.length - 1] = idProduto;
        vendaAtualQuantidades[vendaAtualQuantidades.length - 1] = quantidade;

        System.out.println("Item adicionado à venda com sucesso.");
    }

    // ===== Resumo da venda atual =====
    public static void verResumoVendaAtual() {
        if (vendaAtualIds.length == 0) {
            System.out.println("Nenhum item na venda atual.");
            return;
        }

        double total = 0;
        System.out.println("Resumo da venda atual:");
        for (int i = 0; i < vendaAtualIds.length; i++) {
            int pos = 0;
            for (int j = 0; j < idsProdutos.length; j++) {
                if (idsProdutos[j] == vendaAtualIds[i]) pos = j;
            }
            double valorItem = precoProdutos[pos] * vendaAtualQuantidades[i];
            total += valorItem;
            System.out.printf("Produto: %s | Qtd: %d | Valor: R$ %.2f\n",
                    nomesProdutos[pos], vendaAtualQuantidades[i], valorItem);
        }
        System.out.printf("TOTAL: R$ %.2f\n", total);
    }

    // ===== Finalizar venda =====
    public static void finalizarVenda() {
        if (vendaAtualIds.length == 0) {
            System.out.println("Nenhuma venda para finalizar.");
            return;
        }

        double total = 0;
        for (int i = 0; i < vendaAtualIds.length; i++) {
            int pos = 0;
            for (int j = 0; j < idsProdutos.length; j++) {
                if (idsProdutos[j] == vendaAtualIds[i]) pos = j;
            }
            total += precoProdutos[pos] * vendaAtualQuantidades[i];
            estoquesProdutos[pos] -= vendaAtualQuantidades[i];
        }

        // Adicionar ao histórico
        historicoIdsPedidos = java.util.Arrays.copyOf(historicoIdsPedidos, historicoIdsPedidos.length + 1);
        historicoValoresPedidos = java.util.Arrays.copyOf(historicoValoresPedidos, historicoValoresPedidos.length + 1);

        historicoIdsPedidos[historicoIdsPedidos.length - 1] = proximoIdPedido;
        historicoValoresPedidos[historicoValoresPedidos.length - 1] = total;

        // Adicionar itens vendidos ao histórico
        int linhasAtuais = historicoItensVendidos.length;
        historicoItensVendidos = java.util.Arrays.copyOf(historicoItensVendidos, linhasAtuais + vendaAtualIds.length);
        for (int i = 0; i < vendaAtualIds.length; i++) {
            historicoItensVendidos[linhasAtuais + i] = new int[]{proximoIdPedido, vendaAtualIds[i], vendaAtualQuantidades[i]};
        }

        imprimirNotaFiscal(proximoIdPedido, total);
        proximoIdPedido++;

        // Limpar venda atual
        vendaAtualIds = new int[0];
        vendaAtualQuantidades = new int[0];

        System.out.println("Venda finalizada com sucesso.");
    }

    // ===== Histórico de vendas =====
    public static void verHistoricoVendas() {
        if (historicoIdsPedidos.length == 0) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            System.out.printf("ID do Pedido: %d | Valor Total: R$ %.2f\n",
                    historicoIdsPedidos[i], historicoValoresPedidos[i]);
            System.out.println("Itens Vendidos:");
            for (int j = 0; j < historicoItensVendidos.length; j++) {
                if (historicoItensVendidos[j][0] == historicoIdsPedidos[i]) {
                    System.out.printf("  ID Produto: %d | Quantidade: %d\n",
                            historicoItensVendidos[j][1], historicoItensVendidos[j][2]);
                }
            }
        }
    }

    // ===== Buscar venda específica =====
    public static void buscarVenda() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do Pedido: ");
        int idBusca = sc.nextInt();

        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            if (historicoIdsPedidos[i] == idBusca) {
                imprimirNotaFiscal(idBusca, historicoValoresPedidos[i]);
                return;
            }
        }
        System.out.println("Pedido não encontrado.");
    }

    // ===== Reposição de estoque =====
    public static void reporEstoque() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do produto: ");
        int id = sc.nextInt();

        int pos = -1;
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] == id) pos = i;
        }

        if (pos == -1) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Digite a quantidade para repor: ");
        int qtd = sc.nextInt();

        if (qtd <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        estoquesProdutos[pos] += qtd;
        System.out.println("Estoque atualizado!");
    }

    // ===== Relatório de estoque baixo =====
    public static void relatorioEstoqueBaixo() {
        System.out.println("\nProdutos com estoque baixo (< 10):");
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] != 0 && estoquesProdutos[i] < 10) {
                System.out.printf("ID: %d | Produto: %s | Estoque: %d\n",
                        idsProdutos[i], nomesProdutos[i], estoquesProdutos[i]);
            }
        }
    }

    // ===== Nota fiscal =====
    public static void imprimirNotaFiscal(int idPedido, double total) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("*********************************************************************************************");
        System.out.println("* MACKSHOP                                                                                 *");
        System.out.println("* CNPJ: 12.345.678/0001-99                                                                  *");
        System.out.println("*********************************************************************************************");
        System.out.printf("* NOTA FISCAL - VENDA AO CONSUMIDOR | Pedido ID: %d | Data: %s\n", idPedido, agora.format(fmt));
        System.out.println("*********************************************************************************************");
        System.out.println("* # | ID   | DESCRIÇÃO         | QTD | VL. UNIT. | VL. TOTAL                               *");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        double subtotal = 0;
        int count = 1;
        for (int i = 0; i < historicoItensVendidos.length; i++) {
            if (historicoItensVendidos[i][0] == idPedido) {
                int id = historicoItensVendidos[i][1];
                int qtd = historicoItensVendidos[i][2];
                int pos = 0;
                for (int j = 0; j < idsProdutos.length; j++) {
                    if (idsProdutos[j] == id) pos = j;
                }
                double valorUnitario = precoProdutos[pos];
                double valorTotal = qtd * valorUnitario;
                subtotal += valorTotal;
                System.out.printf("* %d | %d | %-16s | %3d | R$ %7.2f | R$ %7.2f\n",
                        count++, id, nomesProdutos[pos], qtd, valorUnitario, valorTotal);
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("* SUBTOTAL | R$ %.2f\n", subtotal);
        System.out.printf("* TOTAL    | R$ %.2f\n", total);
        System.out.println("*********************************************************************************************");
        System.out.println("* OBRIGADO PELA PREFERÊNCIA! VOLTE SEMPRE!                                                 *");
        System.out.println("*********************************************************************************************");
    }
}
